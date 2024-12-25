package com.pritam.pocketplan.exceptions;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {
    private String message;
    private Integer statusCode;  // Change to HttpStatus instead of Integer

    public AppException(String message, Integer statusCode) {
        super(message);  // Pass message to the superclass (RuntimeException)
        this.message = message;
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
