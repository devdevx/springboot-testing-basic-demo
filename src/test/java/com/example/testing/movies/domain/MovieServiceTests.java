package com.example.testing.movies.domain;

import com.example.testing.movies.domain.model.Movie;
import com.example.testing.movies.domain.model.SearchMovie;
import com.example.testing.movies.domain.provider.MovieSearcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example.testing.testutils.ObjectGenerator.initRandomInstance;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTests {

    @Mock
    private MovieSearcher searcher;

    @InjectMocks
    private MovieService service;

    @Captor
    private ArgumentCaptor<String> titleCaptor;

    @Test
    void givenATitleWhenSearchThenReturnMovieAndPersistStatistics() {

        SearchMovie search = initRandomInstance(SearchMovie.class);
        Movie movie = initRandomInstance(Movie.class);

        when(searcher.search(any())).thenReturn(movie);

        Movie result = service.search(search);

        verify(searcher).search(titleCaptor.capture());
        verifyNoMoreInteractions(searcher);

        assertEquals(movie, result);
        assertEquals(search.getTitle(), titleCaptor.getValue());
    }
}
