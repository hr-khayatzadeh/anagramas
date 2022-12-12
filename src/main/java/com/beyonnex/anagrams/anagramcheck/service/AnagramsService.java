package com.beyonnex.anagrams.anagramcheck.service;

import com.beyonnex.anagrams.anagramcheck.dto.AnagramResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AnagramsService {
    public AnagramResponse isAnagrams(List<String> texts) {
        // remove spaces and duplicate entries
        var dignifiedTexts = texts.stream().map((t) -> {
            var trimmedText = t.replaceAll("[^a-zA-Z0-9_-s+]", "");
            var chars = trimmedText.toLowerCase().toCharArray();
            Arrays.sort(chars);
            return String.valueOf(chars);
        }).distinct().filter(t->t.length() > 2).toList();
        return new AnagramResponse(UUID.randomUUID(), dignifiedTexts.size() == 1, texts) ;
    }

}