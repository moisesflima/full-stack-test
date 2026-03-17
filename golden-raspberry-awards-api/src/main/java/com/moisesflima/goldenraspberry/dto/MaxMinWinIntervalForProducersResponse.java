package com.moisesflima.goldenraspberry.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Response containing min and max win intervals for producers")
public record MaxMinWinIntervalForProducersResponse(
        @JsonProperty("min") List<ProducerIntervalDto> min,
        @JsonProperty("max") List<ProducerIntervalDto> max
) {
    @Schema(description = "Producer interval details")
    public record ProducerIntervalDto(
            @JsonProperty("producer") String producer,
            @JsonProperty("interval") int interval,
            @JsonProperty("previousWin") int previousWin,
            @JsonProperty("followingWin") int followingWin
    ) {}
}
