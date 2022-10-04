package com.reactor.tsunami.model.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash(value = "tokens", timeToLive = 604_800L)
public class TokenHash {
    @Id
    private String id;
    private String token;
}
