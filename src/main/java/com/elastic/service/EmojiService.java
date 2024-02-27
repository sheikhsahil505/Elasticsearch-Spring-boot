package com.elastic.service;
import com.elastic.model.Emoji;
import com.elastic.model.Product;
import com.elastic.repository.EmojiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmojiService {
    @Autowired
    private EmojiRepository emojiRepository;

    public Iterable<Emoji> saveEmojis(List<Emoji> emojis) {
        return emojiRepository.saveAll(emojis);
    }

    public Iterable<Emoji> getAll() {
        return  emojiRepository.findAll();
    }

    public List<Emoji> searchAllFields(String query) {
        return emojiRepository.searchAllFields(query);
    }
}
