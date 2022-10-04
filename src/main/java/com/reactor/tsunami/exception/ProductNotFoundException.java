package com.reactor.tsunami.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends RuntimeException implements TsunamiException{

    public ProductNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }
}
