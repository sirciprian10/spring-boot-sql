package com.example.demo.Exception;

import org.springframework.http.HttpStatus;

public class ProductException {

    private final String message;
    private final HttpStatus httpStatus;

    public ProductException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
