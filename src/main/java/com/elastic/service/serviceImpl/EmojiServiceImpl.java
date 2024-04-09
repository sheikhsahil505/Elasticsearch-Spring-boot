package com.elastic.service.serviceImpl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.elastic.model.Emoji;
import com.elastic.repository.EmojiRepository;
import com.elastic.service.EmojiService;
import com.elastic.util.ElasticSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.function.Supplier;
import java.util.List;


@Service
public class EmojiServiceImpl implements EmojiService {
    @Autowired
    private EmojiRepository emojiRepository;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Override
    public Iterable<Emoji> saveEmojis(List<Emoji> emojis) {
        return emojiRepository.saveAll(emojis);
    }

    @Override
    public Iterable<Emoji> getAll() {
        return emojiRepository.findAll();
    }

    @Override
    public List<Emoji> searchAllFields(String query) {
        return emojiRepository.searchAllFields(query);
    }

    public SearchResponse<Emoji> multiMatch(String key, List<String> fields) throws IOException {
        Supplier<Query> supplier = ElasticSearchUtil.supplierQueryForMultiMatchQuery(key, fields);
        return elasticsearchClient.search(s -> s.index("emojis").query(supplier.get()), Emoji.class);
    }
}