package com.moisesflima.goldenraspberry.exception;

/**
 * Custom domain exception for business rule violations.
 */
public class MovieBusinessException extends RuntimeException {
    public MovieBusinessException(String message) {
        super(message);
    }
}
