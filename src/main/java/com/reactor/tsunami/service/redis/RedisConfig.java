package com.reactor.tsunami.service.redis;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig { //TODO play with redis-reactive in another project

    @Bean
    public ReactiveRedisTemplate<String, String> reactiveRedisTemplate(
            ReactiveRedisConnectionFactory factory) {

        var keySerializer = new StringRedisSerializer();
        var valueSerializer = new Jackson2JsonRedisSerializer<>(String.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, String> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);

        var context =
                builder.value(valueSerializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }

}
