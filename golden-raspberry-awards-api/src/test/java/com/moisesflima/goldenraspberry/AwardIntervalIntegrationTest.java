package com.moisesflima.goldenraspberry;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moisesflima.goldenraspberry.dto.MaxMinWinIntervalForProducersResponse;
import com.moisesflima.goldenraspberry.entity.Movie;
import com.moisesflima.goldenraspberry.repository.MovieRepository;
import com.moisesflima.goldenraspberry.service.AwardIntervalService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AwardIntervalIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private AwardIntervalService awardIntervalService;

    private static final String API_URL = "/api/movies/maxMinWinIntervalForProducers";

    @BeforeEach
    void setUp() {
        movieRepository.deleteAll();
    }

    @Test
    @DisplayName("Should handle complex scenario: Ties, Multiple Wins, and Single Win Producers")
    void shouldHandleComplexLogicScenario() throws Exception {
        // GIVEN
        saveWinner("Producer A", 1980);
        saveWinner("Producer A", 1981);
        saveWinner("Producer A", 1990);
        saveWinner("Producer B", 1999);
        saveWinner("Producer B", 2000);
        saveWinner("Producer C", 1980);
        saveWinner("Producer C", 2000);
        saveWinner("Producer D", 2010);

        // WHEN
        String jsonResponse = mockMvc.perform(get(API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        MaxMinWinIntervalForProducersResponse response = 
                objectMapper.readValue(jsonResponse, MaxMinWinIntervalForProducersResponse.class);

        // THEN
        assertAll(
            () -> assertThat(response.min())
                    .hasSize(2)
                    .extracting("producer")
                    .containsExactlyInAnyOrder("Producer A", "Producer B"),
            
            () -> assertThat(response.min())
                    .allMatch(dto -> dto.interval() == 1),

            () -> assertThat(response.max())
                    .hasSize(1)
                    .first()
                    .satisfies(dto -> {
                        assertThat(dto.producer()).isEqualTo("Producer C");
                        assertThat(dto.interval()).isEqualTo(20);
                        assertThat(dto.previousWin()).isEqualTo(1980);
                        assertThat(dto.followingWin()).isEqualTo(2000);
                    })
        );
    }

    @Test
    @DisplayName("Should return 400 with strict JSON format on Business Exception")
    void shouldReturn400OnException() throws Exception {
        doThrow(new IllegalArgumentException("Invalid data state")).when(awardIntervalService).getAwardIntervals();

        mockMvc.perform(get(API_URL))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Invalid data state"))
                .andExpect(jsonPath("$.timestamp").value(Matchers.matchesPattern("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")));
    }

    @Test
    @DisplayName("Should return 500 with strict JSON format on System Error")
    void shouldReturn500OnException() throws Exception {
        doThrow(new RuntimeException("Critical failure")).when(awardIntervalService).getAwardIntervals();

        mockMvc.perform(get(API_URL))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    private void saveWinner(String producer, int year) {
        Movie movie = new Movie();
        movie.setProducers(producer);
        movie.setYear(year);
        movie.setWinner(true);
        movie.setTitle("Test Movie " + year);
        movie.setStudios("Test Studio");
        movieRepository.save(movie);
    }
}
