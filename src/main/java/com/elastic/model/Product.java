package com.elastic.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Generated;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
@Data
@Document(indexName = "products" ,createIndex = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
//@Mapping(mappingPath = "static/author.json")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private double price;
    private String category;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Product product = (Product) obj;

        return id != null ? id.equals(product.id) : product.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
