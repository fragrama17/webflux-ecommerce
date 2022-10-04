package com.reactor.tsunami.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
@Slf4j
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof TsunamiException) {
            exchange.getResponse().setStatusCode(((TsunamiException) ex).getStatusCode());
            return writeErrorMessage(exchange, ex.getMessage());
        }

        ex.printStackTrace();
        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return writeErrorMessage(exchange, ex.getMessage() + "\n" + Arrays.toString(ex.getStackTrace()));
    }

    private Mono<Void> writeErrorMessage(ServerWebExchange exchange, String message) {
        var bytes = message.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(buffer));
    }

}
