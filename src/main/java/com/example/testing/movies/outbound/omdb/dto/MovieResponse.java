package com.example.testing.movies.outbound.omdb.dto;

import com.example.testing.movies.domain.model.Movie;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MovieResponse {
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Genre")
    private String genres;
    @JsonProperty("Plot")
    private String plot;
    @JsonProperty("Year")
    private Integer year;

    public static Movie toMovie(MovieResponse movieResponse) {
        List<String> genres =
                Arrays.stream(movieResponse.genres.split(",")).map(String::trim).collect(Collectors.toList());
        return new Movie(movieResponse.title, movieResponse.year, movieResponse.plot, genres);
    }
}
