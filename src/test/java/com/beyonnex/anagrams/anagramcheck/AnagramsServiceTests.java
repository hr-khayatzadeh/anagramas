package com.beyonnex.anagrams.anagramcheck;

import com.beyonnex.anagrams.anagramcheck.service.AnagramsService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AnagramsServiceTests {

    @Autowired
    private AnagramsService anagramsService;

    @ParameterizedTest
    @MethodSource("provideAnagrams")
    void isAnagramsReturnsTrue(List<String> texts) {
        assertTrue(anagramsService.isAnagrams(texts).getResult());
    }

    private static Stream<Arguments> provideAnagrams() {
        return Stream.of(
                Arguments.of(List.of("anagram", "nag a ram")),
                Arguments.of(List.of("binary", "Brainy")),
                Arguments.of(List.of("adobe", "abode")),
                Arguments.of(List.of("New York Times", "monkeys write")),
                Arguments.of(List.of("Church of Scientology", "rich-chosen goofy cult"),
                        Arguments.of(List.of("She Sells Sanctuary", "Santa; shy, less cruel", "Satan; cruel, less shy")))
        );
    }

    @ParameterizedTest
    @MethodSource("provideAntiPatternAnagrams")
    void isAnagramsReturnsFalse(List<String> texts) {
        assertFalse(anagramsService.isAnagrams(texts).getResult());
    }

    private static Stream<Arguments> provideAntiPatternAnagrams() {
        return Stream.of(
                Arguments.of(List.of("anagram", "nag a ram m")),
                Arguments.of(List.of("bin", "nibb")),
                Arguments.of(List.of("adobe", "abood")),
                Arguments.of(List.of("", "")),
                Arguments.of(List.of(" ", "")),
                Arguments.of(List.of("", " ")),
                Arguments.of(List.of("ab", "ba"))
        );
    }
}
