package net.hasagj.backend.exceptions;

import net.hasagj.backend.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFound(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionResponse(
                404,
                e.getMessage(),
                LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(BadArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleBadArgument(BadArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionResponse(
                        400,
                        e.getMessage(),
                        LocalDateTime.now()
                )
        );
    }
}
