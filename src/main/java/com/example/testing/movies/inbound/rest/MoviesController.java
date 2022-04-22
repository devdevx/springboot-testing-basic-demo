package com.example.testing.movies.inbound.rest;

import com.example.testing.movies.domain.MovieService;
import com.example.testing.movies.domain.model.Movie;
import com.example.testing.movies.inbound.rest.dto.MovieResponse;
import com.example.testing.movies.inbound.rest.dto.SearchMovieCommand;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/movies")
public class MoviesController {

    private final MovieService service;

    @PostMapping("/get")
    public ResponseEntity<MovieResponse> searchMovie(@Valid @RequestBody SearchMovieCommand command) {
        Movie movie = service.search(SearchMovieCommand.toSearchMovie(command));
        if (movie == null) return notFound().build();
        return ok(MovieResponse.toMovieResponse(movie));
    }
}
