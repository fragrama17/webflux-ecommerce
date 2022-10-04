package com.reactor.tsunami.controller;

import com.reactor.tsunami.model.domain.User;
import com.reactor.tsunami.model.domain.UserDTO;
import com.reactor.tsunami.service.UserService;
import com.reactor.tsunami.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> addUser(@RequestBody UserDTO userDTO) {
        return userService.addUser(userValidator.validate(userDTO)).log();
    }

    @PutMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<User>> modifyUser(@PathVariable String userId, @RequestBody UserDTO userDTO) {
        return userService.modifyUser(userId, userValidator.validate(userDTO))
                .map(p -> ResponseEntity.of(Optional.of(p)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))
                .log();
    }

}
