package com.moisesflima.goldenraspberry.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ApiErrorResponse(
        String status,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime timestamp,
        String message
) {}
