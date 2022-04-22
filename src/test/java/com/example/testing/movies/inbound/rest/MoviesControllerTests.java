package com.example.testing.movies.inbound.rest;

import com.example.testing.movies.domain.MovieService;
import com.example.testing.movies.domain.model.Movie;
import com.example.testing.movies.domain.model.SearchMovie;
import com.example.testing.movies.inbound.rest.dto.SearchMovieCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.testing.testutils.ObjectGenerator.initRandomInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MoviesController.class)
class MoviesControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<SearchMovie> captor;

    @Test
    void givenValidRequestThenResponseOk() throws Exception {

        SearchMovieCommand command = initRandomInstance(SearchMovieCommand.class);
        Movie movie = initRandomInstance(Movie.class);

        when(service.search(any())).thenReturn(movie);

        this.mockMvc.perform(post("/movies/get")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(movie.getTitle()))
                .andExpect(jsonPath("$.year").value(movie.getYear()))
                .andExpect(jsonPath("$.plot").value(movie.getPlot()))
                .andExpect(jsonPath("$.genres").value(movie.getGenres()));

        verify(service).search(captor.capture());
        verifyNoMoreInteractions(service);

        assertEquals(command.getTitle(), captor.getValue().getTitle());
    }

    @Test
    void givenValidRequestThenResponseNotFound() throws Exception {

        SearchMovieCommand command = initRandomInstance(SearchMovieCommand.class);
        Movie movie = null;

        when(service.search(any())).thenReturn(movie);

        this.mockMvc.perform(post("/movies/get")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(service).search(any());
        verifyNoMoreInteractions(service);
    }

    @Test
    void givenNotValidRequestThenResponseBadRequest() throws Exception {

        SearchMovieCommand command = new SearchMovieCommand();

        this.mockMvc.perform(post("/movies/get")
                        .content(objectMapper.writeValueAsString(command))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());

        verifyNoMoreInteractions(service);
    }
}
