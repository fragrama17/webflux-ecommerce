package com.reactor.tsunami.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends RuntimeException implements TsunamiException{

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.CONFLICT;
    }
}
