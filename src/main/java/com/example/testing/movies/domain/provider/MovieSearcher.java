package com.example.testing.movies.domain.provider;

import com.example.testing.movies.domain.model.Movie;

public interface MovieSearcher {
    Movie search(String title);
}
