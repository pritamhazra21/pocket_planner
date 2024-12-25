package com.pritam.pocketplan.exceptions;

import com.pritam.pocketplan.models.apiReturn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle custom AppException
    @ExceptionHandler(AppException.class)
    public ResponseEntity<apiReturn<Object>> handleAppException(AppException e) {
        apiReturn<Object> apiReturn = buildErrorResponse(e.getStatusCode(), e.getMessage(), e.getMessage());
        return new ResponseEntity<>(apiReturn, HttpStatus.valueOf(e.getStatusCode()));
    }

    // Handle any generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<apiReturn<Object>> handleGenericException(Exception e) {
        apiReturn<Object> apiReturn = buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred",
                e.getMessage());
        return new ResponseEntity<>(apiReturn, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Utility method to build error response
    private apiReturn<Object> buildErrorResponse(int statusCode, String message, String errorDetails) {
        apiReturn<Object> apiReturn = new apiReturn<>();
        apiReturn.setStatusCode(statusCode);
        apiReturn.setMessage(message);
        apiReturn.setData(null);  // No data in error response
        apiReturn.setError(errorDetails);  // Optional error message
        return apiReturn;
    }


}
