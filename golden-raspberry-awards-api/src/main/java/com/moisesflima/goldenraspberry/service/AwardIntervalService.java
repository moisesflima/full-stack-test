package com.moisesflima.goldenraspberry.service;

import com.moisesflima.goldenraspberry.dto.MaxMinWinIntervalForProducersResponse;

/**
 * Service interface for award interval operations.
 * Defines the contract for processing and retrieving producer award intervals.
 */
public interface AwardIntervalService {
    
    /**
     * Calculates and returns the producers with the maximum and minimum award intervals.
     * 
     * @return MaxMinWinIntervalForProducersResponse containing the calculation results.
     */
    MaxMinWinIntervalForProducersResponse getAwardIntervals();
}
