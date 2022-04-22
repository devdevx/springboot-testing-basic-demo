package com.example.testing.movies.inbound.rest.dto;

import com.example.testing.movies.domain.model.SearchMovie;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SearchMovieCommand {
    @NotNull
    @NotEmpty
    private String title;

    public static SearchMovie toSearchMovie(SearchMovieCommand command) {
        return new SearchMovie(command.title);
    }
}
