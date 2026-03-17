package com.moisesflima.goldenraspberry.service;

import com.moisesflima.goldenraspberry.dto.MaxMinWinIntervalForProducersResponse;
import com.moisesflima.goldenraspberry.dto.MaxMinWinIntervalForProducersResponse.ProducerIntervalDto;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Analytical component for determining minimum and maximum award intervals.
 */
@Component
public class AwardIntervalAnalyst {

    public MaxMinWinIntervalForProducersResponse analyze(List<ProducerIntervalDto> intervals) {
        if (intervals == null || intervals.isEmpty()) {
            return new MaxMinWinIntervalForProducersResponse(List.of(), List.of());
        }

        int minInterval = intervals.stream()
                .mapToInt(ProducerIntervalDto::interval)
                .min()
                .orElse(0);

        int maxInterval = intervals.stream()
                .mapToInt(ProducerIntervalDto::interval)
                .max()
                .orElse(0);

        List<ProducerIntervalDto> minList = intervals.stream()
                .filter(dto -> dto.interval() == minInterval)
                .toList();

        List<ProducerIntervalDto> maxList = intervals.stream()
                .filter(dto -> dto.interval() == maxInterval)
                .toList();

        return new MaxMinWinIntervalForProducersResponse(minList, maxList);
    }
}
