package com.example.testing.movies.outbound.omdb;

import com.example.testing.movies.domain.model.Movie;
import com.example.testing.movies.domain.provider.MovieSearcher;
import com.example.testing.movies.outbound.omdb.dto.MovieResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@AllArgsConstructor
public class OMDbApiMovieSearcher implements MovieSearcher {
    private final RestTemplate restTemplate;
    private final OMDbProperties properties;
    private final ObjectMapper objectMapper;

    @Override
    public Movie search(String title) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(properties.getBaseUrl())
                    .queryParam("t", title)
                    .queryParam("plot", "full")
                    .queryParam("apikey", properties.getApiKey())
                    .encode().toUriString();
            JsonNode json = restTemplate.getForObject(url, JsonNode.class);
            if (isValidResponse(json)) {
                MovieResponse response = objectMapper.treeToValue(json, MovieResponse.class);
                return MovieResponse.toMovie(response);
            }
        } catch (Exception e) {
            log.error("Error retrieving movie with title '{}'", title, e);
        }
        return null;
    }

    private boolean isValidResponse(JsonNode json) {
        if (json == null) {
            return false;
        }
        String flag = json.get("Response").asText();
        return "True".equals(flag);
    }
}
