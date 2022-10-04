package com.reactor.tsunami.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends RuntimeException implements TsunamiException{

    public ForbiddenException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.FORBIDDEN;
    }
}
