package com.moisesflima.goldenraspberry.service;

import com.moisesflima.goldenraspberry.dto.MaxMinWinIntervalForProducersResponse;
import com.moisesflima.goldenraspberry.dto.MaxMinWinIntervalForProducersResponse.ProducerIntervalDto;
import com.moisesflima.goldenraspberry.entity.Movie;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class IntervalCalculator {

    private final ProducerParser producerParser;

    public IntervalCalculator(ProducerParser producerParser) {
        this.producerParser = producerParser;
    }

    public MaxMinWinIntervalForProducersResponse calculateAndAnalyze(
            List<Movie> winners,
            AwardIntervalAnalyst.Builder builder) {

        Map<String, Integer> lastYearByProducer = new HashMap<>();

        for (Movie movie : winners) {
            int year = movie.getYear();
            for (String producer : producerParser.parse(movie.getProducers())) {
                Integer lastYear = lastYearByProducer.put(producer, year);
                if (lastYear != null && lastYear < year) {
                    builder.accept(new ProducerIntervalDto(producer, year - lastYear, lastYear, year));
                }
            }
        }

        return builder.build();
    }
}
