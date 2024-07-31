package com.vasilis_timoudas.movies_review_api;


import com.vasilis_timoudas.movies_review_api.model.Movie;
import com.vasilis_timoudas.movies_review_api.repository.MovieRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    private Movie movie;

    @BeforeEach
    public void setUp() {
        movieRepository.deleteAll();
        movie = new Movie("tt1234567", "Test Title", "2023-01-01", "link", "poster", Arrays.asList("backdrop1"), Arrays.asList("genre1"));
        movieRepository.save(movie);
    }

    @AfterEach
    public void tearDown() {
        movieRepository.deleteAll();
    }

    @Test
    public void testGetMovies_Success() throws Exception {
        mockMvc.perform(get("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].imdbId").value(movie.getImdbId()));
    }

    @Test
    public void testGetMovie_Success() throws Exception {
        mockMvc.perform(get("/api/v1/movies/{imdbId}", movie.getImdbId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imdbId").value(movie.getImdbId()));
    }

    @Test
    public void testGetSingleMovieFailure() throws Exception {
        mockMvc.perform(get("/api/v1/movies/{imdbId}", "nonexistentId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imdbId").doesNotExist());
    }

}
