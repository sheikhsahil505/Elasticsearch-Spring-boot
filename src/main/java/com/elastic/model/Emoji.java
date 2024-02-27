package com.elastic.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
@Data
@Document(indexName = "emojis" ,createIndex = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Emoji {
    @Id
    private String id;
    private String emoji;
    private String name;
    private String shortname;
    private String unicode;
    private String html;
    private String category;
    private String order;

}
