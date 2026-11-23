package net.hasagj.backend.dto;

import java.time.LocalDateTime;

public record ExceptionResponse(
        int statusCode,
        String message,
        LocalDateTime  timestamp
) { }
