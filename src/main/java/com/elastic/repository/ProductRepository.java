package com.elastic.repository;
import com.elastic.model.Product;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    @Query("{\"query_string\": {\"query\": \"*?0*\"}}")
    List<Product> searchAllFields(String query);

}
