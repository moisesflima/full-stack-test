package com.moisesflima.goldenraspberry.service;

import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

/**
 * Split producers string into individual producer names.
 */
@Component
public class ProducerParser {

    private static final String SEPARATORS_REGEX = ",\\s+and\\s+|,\\s*|\\s+and\\s+";

    /**
     * Splits a producers string into individual producer names.
     * Example: "Producer 1, Producer 2 and Producer 3"
     */
    public List<String> parse(String producers) {
        if (producers == null || producers.isBlank()) {
            return List.of();
        }
        
        String[] parts = producers.split(SEPARATORS_REGEX);
        return Arrays.stream(parts)
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
    }
}
