package com.reactor.tsunami.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "products", timeToLive = 3600L)
public class ProductsCache {
    @Id
    private String id;
    private Page<Product> pagedProducts;
}
