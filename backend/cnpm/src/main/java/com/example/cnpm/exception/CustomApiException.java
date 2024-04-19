package com.example.cnpm.exception;

import org.springframework.http.HttpStatus;

public class CustomApiException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;

    public CustomApiException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public CustomApiException(String message, HttpStatus httpStatus, String message1) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message1;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
