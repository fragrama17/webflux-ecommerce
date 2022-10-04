package com.reactor.tsunami.service;

import com.reactor.tsunami.model.domain.User;
import com.reactor.tsunami.model.domain.UserDTO;
import com.reactor.tsunami.exception.UserAlreadyExistsException;
import com.reactor.tsunami.model.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> addUser(UserDTO userDTO) {
        return userRepository.findUserByUsernameOrEmail(userDTO.getUsername(), userDTO.getEmail())
                .filter(Objects::nonNull)
                .handle((user, synchronousSink) -> synchronousSink.error(new UserAlreadyExistsException("user already exists")))
                .then(userRepository.save(
                        User.builder()
                                .username(userDTO.getUsername())
                                .email(userDTO.getEmail())
                                .password(userDTO.getPassword())
                                .timestampInsert(LocalDateTime.now())
                                .isSubscribedToNewsLetter(true)
                                .build()
                ));

    }

    public Mono<User> modifyUser(String userId, UserDTO userDTO) {
        return userRepository.findUserByUsernameOrEmail(userDTO.getUsername(), userDTO.getEmail())
                .filter(Objects::nonNull)
                .handle((user, synchronousSink) -> synchronousSink.error(new UserAlreadyExistsException("user already exists")))
                .then(userRepository.save(
                        User.builder()
                                .id(userId)
                                .username(userDTO.getUsername())
                                .email(userDTO.getEmail())
                                .password(userDTO.getPassword())
                                .timestampUpdate(LocalDateTime.now())
                                .isSubscribedToNewsLetter(userDTO.isSubscribedToNewsLetter())
                                .build()
                ));
    }
}
