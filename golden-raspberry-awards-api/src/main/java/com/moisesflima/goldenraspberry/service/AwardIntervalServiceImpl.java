package com.moisesflima.goldenraspberry.service;

import com.moisesflima.goldenraspberry.dto.MaxMinWinIntervalForProducersResponse;
import com.moisesflima.goldenraspberry.exception.MovieBusinessException;
import com.moisesflima.goldenraspberry.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for processing award intervals.
 */
@Service
public class AwardIntervalServiceImpl implements AwardIntervalService {

    private final MovieRepository movieRepository;
    private final IntervalCalculator intervalCalculator;
    private final AwardIntervalAnalyst analyst;

    public AwardIntervalServiceImpl(MovieRepository movieRepository, 
                                   IntervalCalculator intervalCalculator, 
                                   AwardIntervalAnalyst analyst) {
        this.movieRepository = movieRepository;
        this.intervalCalculator = intervalCalculator;
        this.analyst = analyst;
    }

    @Override
    @Transactional(readOnly = true)
    public MaxMinWinIntervalForProducersResponse getAwardIntervals() {
        try {
            var winningMovies = movieRepository.findAllWinnersOrderedByProducerAndYear();
            
            List<MaxMinWinIntervalForProducersResponse.ProducerIntervalDto> allIntervals = 
                    intervalCalculator.calculateAllIntervals(winningMovies);
                    
            return analyst.analyze(allIntervals);
        } catch (Exception e) {
            throw new MovieBusinessException("Error processing award intervals: " + e.getMessage());
        }
    }
}
