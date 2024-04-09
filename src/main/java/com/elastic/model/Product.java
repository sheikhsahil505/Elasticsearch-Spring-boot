package com.elastic.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@Document(indexName = "products" ,createIndex = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
//@Mapping(mappingPath = "static/author.json")
public class Product {
    @Id
    private String id;
    @Field(type = FieldType.Keyword, name = "name")
    private String name;
    private String description;
    private double price;
    private List<String> category;
}
