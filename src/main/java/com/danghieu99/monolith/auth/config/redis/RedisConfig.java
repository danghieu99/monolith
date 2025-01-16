package com.danghieu99.monolith.auth.config.redis;

import com.danghieu99.monolith.auth.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisTemplate<Integer, Token> redisTokenTemplate() {
        RedisTemplate<Integer, Token> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
