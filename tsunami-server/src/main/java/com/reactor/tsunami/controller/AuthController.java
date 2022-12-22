package com.reactor.tsunami.controller;

import com.reactor.tsunami.exception.UnauthorizedException;
import com.reactor.tsunami.model.domain.TokenHash;
import com.reactor.tsunami.model.domain.UserDTO;
import com.reactor.tsunami.model.repository.UserRepository;
import com.reactor.tsunami.security.TokenStore;
import com.reactor.tsunami.service.kafka.KafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@CrossOrigin
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserRepository userRepository;

    private final TokenStore tokenStore;

    private final KafkaService kafkaService;

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TokenHash> authentication(@RequestBody UserDTO userDTO) {
        return userRepository.findUserByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword())
                .switchIfEmpty(Mono.error(() -> {
                    kafkaService.logUserAccess(userDTO.getUsername(), false, LocalDateTime.now().toString());
                    return new UnauthorizedException("invalid username or password");
                }))
                .flatMap(user -> Mono.just(tokenStore.updateTokenByUserId(user.getId())))
                .doOnSuccess(tokenHash -> kafkaService.logUserAccess(tokenHash.getId(), true, LocalDateTime.now().toString()))
                .log();
    }

    @GetMapping(path = "/refresh-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TokenHash> refreshToken(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token, @RequestHeader String user) {
        return token.equals(tokenStore.getTokenByUserId(user)) ?
                Mono.just(tokenStore.updateTokenByUserId(user)) : Mono.error(() -> new UnauthorizedException("token is not valid"));

    }

    @PostMapping(path = "/reset-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> resetPassword(@RequestBody UserDTO userDTO) {
        return userRepository.findUserByEmail(userDTO.getEmail())
//                .doOnSuccess(user -> sendEmail(.....))
                .switchIfEmpty(Mono.error(() -> new UnauthorizedException("no user registered with this email")))
                .flatMap(user -> Mono.empty());
    }

}
