package com.moisesflima.goldenraspberry.controller;

import com.moisesflima.goldenraspberry.dto.MaxMinWinIntervalForProducersResponse;
import com.moisesflima.goldenraspberry.exception.ApiErrorResponse;
import com.moisesflima.goldenraspberry.service.AwardIntervalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Golden Raspberry Awards endpoints.
 */
@RestController
@RequestMapping("/api/movies")
@Tag(name = "movie-resource", description = "Movie data operations")
public class ProducerController {

        private final AwardIntervalService awardIntervalService;

        public ProducerController(AwardIntervalService awardIntervalService) {
                this.awardIntervalService = awardIntervalService;
        }

        /**
         * GET /api/movies/maxMinWinIntervalForProducers
         *
         * Returns the producer(s) with the minimum and maximum intervals
         * between consecutive Golden Raspberry Award wins.
         */
        @Operation(summary = "Max/Min Win Interval For Producers", description = "Get the producer with the longest and shortest interval between two consecutive awards.", responses = {
                        @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = MaxMinWinIntervalForProducersResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
        })
        @GetMapping("/maxMinWinIntervalForProducers")
        public ResponseEntity<MaxMinWinIntervalForProducersResponse> getAwardIntervals() {
                MaxMinWinIntervalForProducersResponse response = awardIntervalService.getAwardIntervals();
                return ResponseEntity.ok(response);
        }
}
