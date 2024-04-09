package com.elastic.service.serviceImpl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.elastic.exception.ResourceNotFoundException;
import com.elastic.model.Product;
import com.elastic.repository.ProductRepository;
import com.elastic.service.ProductService;
import com.elastic.util.ElasticSearchUtil;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.function.Supplier;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import java.util.*;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    Logger logger= LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private static final String PRODUCT_INDEX = "products";

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ElasticsearchOperations elasticsearchOperations) {
        this.productRepository = productRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Iterable<Product> saveAll(List<Product> product) {
        return productRepository.saveAll(product);
    }

    @Override
    public Optional<Product> findById(String id) {
        Optional<Product> byId = productRepository.findById(id);
        if(byId.isPresent()){
            return byId;
        }
        log.error("user not found with id : "+id );
        throw new ResourceNotFoundException("User not found with id : "+id);
    }

    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public SearchResponse<Product> matchProductsWithName(String fieldValue) throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.supplierWithNameField(fieldValue);
        SearchResponse<Product> searchResponse = elasticsearchClient.search(s -> s.index("products").query(supplier.get()), Product.class);
        return searchResponse;
    }

    @Override
    public SearchResponse<Product> searchProductByAnyField(String keyword, String fieldName) throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.supplierWithFieldAndValue(fieldName, keyword);
        return elasticsearchClient.search(s -> s.index("products").query(supplier.get()), Product.class);
    }


    @Override
    public SearchResponse<Product> fuzzySearch(String approximateProductName) throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.createSupplierQuery(approximateProductName);
        SearchResponse<Product> response = elasticsearchClient
                .search(s -> s.index("products").query(supplier.get()), Product.class);
        return response;
    }


    @Override
    public List<Product> searchAllFields(String query) {
        return productRepository.searchAllFields(query);
    }
}

