package com.elastic.repository;

import com.elastic.model.Emoji;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmojiRepository extends ElasticsearchRepository<Emoji, String> {
    @Query("{\"query_string\": {\"query\": \"*?0*\"}}")
    List<Emoji> searchAllFields(String query);


}
