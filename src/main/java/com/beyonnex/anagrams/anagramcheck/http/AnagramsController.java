package com.beyonnex.anagrams.anagramcheck.http;

import com.beyonnex.anagrams.anagramcheck.dto.AnagramRequest;
import com.beyonnex.anagrams.anagramcheck.dto.AnagramResponse;
import com.beyonnex.anagrams.anagramcheck.service.AnagramsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("anagrams")
public class AnagramsController {

    @Value("classpath:anagrams.json")
    private Resource resource;

    private final AnagramsService anagramsService;

    public AnagramsController(AnagramsService anagramsService) {
        this.anagramsService = anagramsService;
    }

    @Operation(summary = "Check whether the provided collection record are anagrams compatible or not")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Check Anagrams compatibility",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnagramResponse.class)) })})
    @PostMapping(value = "/isAnagrams", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<AnagramResponse> isAnagrams(@RequestBody List<AnagramRequest> anagramRequests) {
        return anagramRequests.stream().map(p -> anagramsService.isAnagrams(p.getTexts())).toList();
    }
    @ApiResponse(responseCode = "200", description = "Return sample data",
            content = { @Content(mediaType = "application/json") })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String samples() {
        try (var reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}