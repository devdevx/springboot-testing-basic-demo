package com.example.testing.movies.outbound.omdb;

import com.example.testing.movies.domain.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.Arrays;

import static com.example.testing.testutils.ResponseReader.readResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@AutoConfigureWebClient(registerRestTemplate = true)
@RestClientTest(OMDbApiMovieSearcher.class)
@EnableConfigurationProperties(OMDbProperties.class)
class OMDbApiMovieSearcherTests {

    @Autowired
    private MockRestServiceServer mockServer;
    @Autowired
    private OMDbProperties properties;
    @Autowired
    private OMDbApiMovieSearcher searcher;

    @Test
    void givenValidResponseThenReturnMovie() {
        mockServer.expect(requestTo(properties.getBaseUrl() + "?t=Alien&plot=full&apikey=key"))
                .andExpect(method(HttpMethod.GET)).andRespond(
                        withStatus(OK).body(readResponse("omdb/movieOk.json")).contentType(MediaType.APPLICATION_JSON));

        Movie movie = searcher.search("Alien");

        assertNotNull(movie);
        assertEquals("Alien", movie.getTitle());
        assertEquals(1979, movie.getYear());
        assertEquals(Arrays.asList("Horror", "Sci-Fi"), movie.getGenres());
        assertNotNull(movie.getPlot());
    }

    @Test
    void givenNotFoundValidResponseThenReturnNull() {
        mockServer.expect(requestTo(properties.getBaseUrl() + "?t=Alien&plot=full&apikey=key"))
                .andExpect(method(HttpMethod.GET)).andRespond(
                        withStatus(OK).body(readResponse("omdb/movieNotFound.json")).contentType(MediaType.APPLICATION_JSON));

        Movie movie = searcher.search("Alien");

        assertNull(movie);
    }

    @Test
    void givenErrorResponseThenReturnNull() {
        mockServer.expect(requestTo(properties.getBaseUrl() + "?t=Alien&plot=full&apikey=key"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON));

        Movie movie = searcher.search("Alien");

        assertNull(movie);
    }
}
