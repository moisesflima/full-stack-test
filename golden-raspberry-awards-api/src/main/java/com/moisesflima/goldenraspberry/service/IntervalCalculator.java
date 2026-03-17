package com.moisesflima.goldenraspberry.service;

import com.moisesflima.goldenraspberry.dto.MaxMinWinIntervalForProducersResponse.ProducerIntervalDto;
import com.moisesflima.goldenraspberry.entity.Movie;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Calculates win intervals for producers from a provided movie list.
 */
@Component
public class IntervalCalculator {

    private final ProducerParser producerParser;

    public IntervalCalculator(ProducerParser producerParser) {
        this.producerParser = producerParser;
    }

    /**
     * Groups wins by producer and calculates all consecutive intervals.
     */
    public List<ProducerIntervalDto> calculateAllIntervals(List<Movie> winners) {
        Map<String, List<Integer>> producerWins = new LinkedHashMap<>();

        // Group years by producer
        for (Movie movie : winners) {
            List<String> producers = producerParser.parse(movie.getProducers());
            for (String producer : producers) {
                producerWins.computeIfAbsent(producer, k -> new ArrayList<>()).add(movie.getYear());
            }
        }

        List<ProducerIntervalDto> allIntervals = new ArrayList<>();

        // Calculate intervals for each producer with at least 2 wins
        for (Map.Entry<String, List<Integer>> entry : producerWins.entrySet()) {
            String producer = entry.getKey();
            List<Integer> years = entry.getValue();
            Collections.sort(years);

            for (int i = 1; i < years.size(); i++) {
                int prev = years.get(i - 1);
                int next = years.get(i);
                allIntervals.add(new ProducerIntervalDto(producer, next - prev, prev, next));
            }
        }

        return allIntervals;
    }
}
