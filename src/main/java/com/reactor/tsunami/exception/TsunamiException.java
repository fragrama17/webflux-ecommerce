package com.reactor.tsunami.exception;

import org.springframework.http.HttpStatus;

public interface TsunamiException {

    HttpStatus getStatusCode();

}
