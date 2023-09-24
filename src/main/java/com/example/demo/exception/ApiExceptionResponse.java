package com.example.demo.exception;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ApiExceptionResponse {
    private final String message;
    private final int statusCode;
    private Instant timestamp;

    public ApiExceptionResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = Instant.now();
    }
}
