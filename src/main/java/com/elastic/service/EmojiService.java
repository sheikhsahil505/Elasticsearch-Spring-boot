package com.elastic.service;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.elastic.model.Emoji;
import com.elastic.repository.EmojiRepository;
import com.elastic.util.ElasticSearchUtil;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.List;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations; // Optional for @Autowired approach
import com.fasterxml.jackson.databind.ObjectMapper; // For JSON mapping in both approaches

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;
@Service
public class EmojiService {
    @Autowired
    private EmojiRepository emojiRepository;

    @Autowired
    private ElasticsearchClient elasticsearchClient;
    public Iterable<Emoji> saveEmojis(List<Emoji> emojis) {
        return emojiRepository.saveAll(emojis);
    }

    public Iterable<Emoji> getAll() {
        return  emojiRepository.findAll();
    }

    public List<Emoji> searchAllFields(String query) {
        return emojiRepository.searchAllFields(query);
    }
    public SearchResponse<Emoji> multiMatch(String key , List<String> fields ) throws IOException {
        Supplier<Query> supplier  = ElasticSearchUtil.supplierQueryForMultiMatchQuery(key,fields);
        return elasticsearchClient.search(s->s.index("emojis").query(supplier.get()), Emoji.class);
    }


}