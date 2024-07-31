package com.vasilis_timoudas.movies_review_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vasilis_timoudas.movies_review_api.repository.ReviewRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        reviewRepository.deleteAll();
    }

    @AfterEach
    public void tearDown() {
        reviewRepository.deleteAll();
    }

    @Test
    public void testCreateReviewSuccess() throws Exception {
        Map<String, String> payload = new HashMap<>();
        payload.put("reviewBody", "This is a great movie!");
        payload.put("imdbId", "tt1234567");

        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body").value("This is a great movie!"))
                .andExpect(jsonPath("$.created").exists())
                .andExpect(jsonPath("$.updated").exists());
    }

    @Test
    public void testCreateReviewFailure() throws Exception {
        Map<String, String> payload = new HashMap<>();
        payload.put("reviewBody", "");
        payload.put("imdbId", "tt1234567");

        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isBadRequest());
    }
}
