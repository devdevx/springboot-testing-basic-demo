package com.example.testing.movies.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Movie {
    private String title;
    private Integer year;
    private String plot;
    private List<String> genres;
}
