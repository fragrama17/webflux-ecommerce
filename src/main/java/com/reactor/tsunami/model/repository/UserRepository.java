package com.reactor.tsunami.model.repository;

import com.reactor.tsunami.model.domain.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findUserByEmail(String email);

    Mono<User> findUserByUsernameAndPassword(String username, String password);

    Mono<User> findUserByUsernameOrEmail(String username, String email);
}
