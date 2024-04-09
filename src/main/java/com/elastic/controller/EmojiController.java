package com.elastic.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.elastic.model.Emoji;
import com.elastic.service.serviceImpl.EmojiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/emojis")
@RestController
public class EmojiController {
    @Autowired
    private EmojiServiceImpl emojiService;

    @PostMapping
    public Iterable<Emoji> saveEmojis(@RequestBody List<Emoji> emojis) {
        return emojiService.saveEmojis(emojis);
    }

    @GetMapping
    public ResponseEntity<Iterable<Emoji>> getAllEmojis() {
        return new ResponseEntity<>(emojiService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public List<Emoji> searchAllFields(@RequestParam String query) {
        return emojiService.searchAllFields(query);
    }

    @GetMapping("/multiMatch")
    public List<String> multiMatch(@RequestParam String key) throws IOException {
        List<String> fields = new ArrayList<String>();
        fields.add("name");
        fields.add("shortname");
        SearchResponse<Emoji> searchResponse =
                emojiService.multiMatch(key, fields);
        List<Hit<Emoji>> listOfHits = searchResponse.hits().hits();
        List<String> listOfUsers = new ArrayList<>();
        for (Hit<Emoji> hit : listOfHits) {
            listOfUsers.add(hit.source().getEmoji());
        }
        return listOfUsers;
    }
}


