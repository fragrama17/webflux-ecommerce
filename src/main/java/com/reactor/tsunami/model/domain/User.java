package com.reactor.tsunami.model.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class User {
    @Id
    @Getter
    private String id;
    private String username;
    private String email;
    private String password;
    private LocalDateTime timestampInsert;
    private LocalDateTime timestampUpdate;
    private boolean isSubscribedToNewsLetter;
}

