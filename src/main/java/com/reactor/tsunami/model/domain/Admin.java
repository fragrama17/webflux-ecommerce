package com.reactor.tsunami.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Document
public class Admin {
    @Id
    private String id;
    private String userId;
    private String email;
    private String password;
    private LocalDateTime timestampInsert;
    private LocalDateTime timestampUpdate;
}
