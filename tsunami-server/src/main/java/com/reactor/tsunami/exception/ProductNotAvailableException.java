package com.reactor.tsunami.exception;

import org.springframework.http.HttpStatus;

public class ProductNotAvailableException extends CustomException {

    public ProductNotAvailableException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
