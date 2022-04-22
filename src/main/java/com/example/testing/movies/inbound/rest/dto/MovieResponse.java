package com.example.testing.movies.inbound.rest.dto;

import com.example.testing.movies.domain.model.Movie;
import lombok.Data;

import java.util.List;

@Data
public class MovieResponse {
    private String title;
    private Integer year;
    private String plot;
    private List<String> genres;

    public static MovieResponse toMovieResponse(Movie movie) {
        MovieResponse response = new MovieResponse();
        response.title = movie.getTitle();
        response.year = movie.getYear();
        response.plot = movie.getPlot();
        response.genres = movie.getGenres();
        return response;
    }
}
