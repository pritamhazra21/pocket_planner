package com.pritam.pocketplan.exceptions;

import com.pritam.pocketplan.models.ApiReturn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle custom AppException
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiReturn<Object>> handleAppException(AppException e) {
        ApiReturn<Object> apiReturn = buildErrorResponse(e.getStatusCode(), e.getMessage(), e.getMessage());
        return new ResponseEntity<>(apiReturn, HttpStatus.valueOf(e.getStatusCode()));
    }

    // Handle any generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiReturn<Object>> handleGenericException(Exception e) {
        ApiReturn<Object> apiReturn = buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred",
                e.getMessage());
        return new ResponseEntity<>(apiReturn, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Utility method to build error response
    private ApiReturn<Object> buildErrorResponse(int statusCode, String message, String errorDetails) {
        ApiReturn<Object> apiReturn = new ApiReturn<>();
        apiReturn.setStatusCode(statusCode);
        apiReturn.setMessage(message);
        apiReturn.setData(null);  // No data in error response
        apiReturn.setError(errorDetails);  // Optional error message
        return apiReturn;
    }


}
