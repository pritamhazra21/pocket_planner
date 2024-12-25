package com.pritam.pocketplan.models;

public class apiReturn<T> {

    private Integer statusCode;  // Numeric status code (200, 400, etc.)
    private String message;      // Error message or success message
    private T data;              // The actual data (e.g., the saved TransactionEntry)
    private String error;        // Optional: any error message if something goes wrong

    // Constructors
    public apiReturn(Integer statusCode, String message, T data, String error) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.error = error;
    }

    public apiReturn(Integer statusCode, String message, T data) {
        this(statusCode, message, data, null);
    }

    public apiReturn(Integer statusCode, String message, String error) {
        this(statusCode, message, null, error);
    }

    public apiReturn() {
    }

    // Getters and setters
    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
