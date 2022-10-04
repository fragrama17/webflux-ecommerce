package com.reactor.tsunami.security;

import com.reactor.tsunami.model.domain.TokenHash;
import com.reactor.tsunami.exception.UnauthorizedException;
import com.reactor.tsunami.model.repository.TokenStoreRepository;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class TokenStore {
    private final TokenStoreRepository tokenStoreRepository;
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public TokenStore(TokenStoreRepository tokenStoreRepository) {
        this.tokenStoreRepository = tokenStoreRepository;
    }

    public TokenHash updateTokenByUserId(String userId) {
        tokenStoreRepository.save(new TokenHash(userId, generateNewToken()));
        return tokenStoreRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("transaction corrupted while saving new security-token"));
    }

    public String getTokenByUserId(String userId) {
        return tokenStoreRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("need to authenticate first"))
                .getToken();
    }

    private String generateNewToken() {
        var randomBytes = new byte[2048];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

}
