package com.reactor.tsunami.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends RuntimeException implements TsunamiException{

    public UnauthorizedException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.UNAUTHORIZED;
    }
}
