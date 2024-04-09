package com.elastic.service;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.elastic.model.Product;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product save(Product product);
    Iterable<Product> saveAll(List<Product> product);
    Optional<Product> findById(String id);
    Iterable<Product> findAll();
    void deleteById(String id);
    SearchResponse<Product> matchProductsWithName(String fieldValue) throws IOException;
    SearchResponse<Product> searchProductByAnyField(String keyword, String fieldName) throws IOException;
    SearchResponse<Product> fuzzySearch(String approximateProductName) throws IOException;
    List<Product> searchAllFields(String query);
}
