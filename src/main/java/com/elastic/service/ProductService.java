package com.elastic.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import com.elastic.model.Product;
import com.elastic.repository.ProductRepository;
import com.elastic.util.ElasticSearchUtil;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.function.Supplier;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;


@Service

public class ProductService {

    private final ProductRepository productRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private static final String PRODUCT_INDEX = "products";

    @Autowired
    public ProductService(ProductRepository productRepository, ElasticsearchOperations elasticsearchOperations) {
        this.productRepository = productRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Iterable<Product> saveAll(List<Product> product) {
        return productRepository.saveAll(product);
    }

    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    public SearchResponse<Product> matchProductsWithName(String fieldValue) throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.supplierWithNameField(fieldValue);
        SearchResponse<Product> searchResponse = elasticsearchClient.search(s -> s.index("products").query(supplier.get()), Product.class);
        return searchResponse;
    }

    public SearchResponse<Product> searchProductByAnyField(String keyword, String fieldName) throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.supplierWithFieldAndValue(fieldName, keyword);
        return elasticsearchClient.search(s -> s.index("products").query(supplier.get()), Product.class);
    }


    public SearchResponse<Product> fuzzySearch(String approximateProductName) throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.createSupplierQuery(approximateProductName);
        SearchResponse<Product> response = elasticsearchClient
                .search(s -> s.index("products").query(supplier.get()), Product.class);
        return response;
    }


    public List<Product> searchAllFields(String query) {
        return productRepository.searchAllFields(query);
    }
}

