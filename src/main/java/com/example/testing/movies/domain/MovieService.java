package com.example.testing.movies.domain;

import com.example.testing.movies.domain.model.Movie;
import com.example.testing.movies.domain.model.SearchMovie;
import com.example.testing.movies.domain.provider.MovieSearcher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieService {
    private final MovieSearcher searcher;

    public Movie search(SearchMovie searchMovie) {
        return searcher.search(searchMovie.getTitle());
    }
}
