package com.elastic.controller;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.elastic.model.Product;
import com.elastic.service.ProductService;
import com.elastic.service.serviceImpl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }
    @PostMapping("/saveAll")
    public Iterable<Product> createProducts(@RequestBody List<Product> product) {
        return productService.saveAll(product);
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable String id) {
        log.info("user get by id : "+id);
        return productService.findById(id);
    }

    @GetMapping
    public Iterable<Product> getAllProducts() {
        return productService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteById(id);
    }
    @GetMapping("/matchAllProducts/{fieldValue}")
    public List<Product> matchAllProductsWithName(@PathVariable String fieldValue) throws IOException {
        SearchResponse<Product> searchResponse =  productService.matchProductsWithName(fieldValue);
        List<Hit<Product>> listOfHits= searchResponse.hits().hits();
        List<Product> listOfProducts  = new ArrayList<>();
        for(Hit<Product> hit : listOfHits){
            Product source = hit.source();
            source.setId(hit.id());
            listOfProducts.add(source);
        }
        return listOfProducts;
    }

    @GetMapping("/searchByFieldName")
    public List<Product> matchAllProductWithAnyField(@RequestParam("fieldName") String fieldName,@RequestParam("keyword") String keyword) throws IOException {
        SearchResponse<Product> searchResponse =  productService.searchProductByAnyField(fieldName,keyword);
        List<Hit<Product>> listOfHits= searchResponse.hits().hits();
        List<Product> listOfProducts  = new ArrayList<>();
        for(Hit<Product> hit : listOfHits){
            Product source = hit.source();
            source.setId(hit.id());
            listOfProducts.add(source);
        }
        return listOfProducts;
    }
    @GetMapping("/fuzzySearch/{approximateProductName}")
    List<Product> fuzzySearch( @PathVariable String approximateProductName ) throws IOException {
        SearchResponse<Product> searchResponse = productService.fuzzySearch(approximateProductName);
        List<Hit<Product>> hitList = searchResponse.hits().hits();
        List<Product> productList = new ArrayList<>();
        for(Hit<Product> hit :hitList){
            Product source = hit.source();
            source.setId(hit.id());
            productList.add(source);
        }
        return productList;
    }
    @GetMapping("/search")
    public List<Product> searchAllFields(@RequestParam String query) {
        return productService.searchAllFields(query);
    }

}
