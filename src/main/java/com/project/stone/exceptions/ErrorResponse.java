package com.project.stone.exceptions;

public class ErrorResponse {
    private final String errorCode;
    private final String errorMessage;

    // Default constructor
    public ErrorResponse() {
        this.errorCode = "";
        this.errorMessage = "";
    }

    public ErrorResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    // Getters for errorCode and errorMessage
    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}