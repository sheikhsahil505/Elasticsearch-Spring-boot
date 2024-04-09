package com.elastic.service;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.elastic.model.Emoji;

import java.io.IOException;
import java.util.List;

public interface EmojiService {
    Iterable<Emoji> saveEmojis(List<Emoji> emojis);
    Iterable<Emoji> getAll();
    List<Emoji> searchAllFields(String query);
    SearchResponse<Emoji> multiMatch(String key, List<String> fields) throws IOException;
}
