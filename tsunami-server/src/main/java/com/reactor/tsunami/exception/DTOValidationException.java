package com.reactor.tsunami.exception;

import org.springframework.http.HttpStatus;

public class DTOValidationException extends CustomException {

    public DTOValidationException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
