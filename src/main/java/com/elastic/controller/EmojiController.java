package com.elastic.controller;

import com.elastic.model.Emoji;
import com.elastic.model.Product;
import com.elastic.service.EmojiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RequestMapping("/emojis")
@RestController
public class EmojiController {
    @Autowired
    private EmojiService emojiService;

    @PostMapping
    public Iterable<Emoji> saveEmojis(@RequestBody List<Emoji> emojis) {
        return emojiService.saveEmojis(emojis);
    }
    @GetMapping
    public ResponseEntity<Iterable<Emoji>> getAllEmojis(){
        return new ResponseEntity<>( emojiService.getAll(),HttpStatus.OK);
    }
    @GetMapping("/search")
    public List<Emoji> searchAllFields(@RequestParam String query) {
        return emojiService.searchAllFields(query);
    }
}

