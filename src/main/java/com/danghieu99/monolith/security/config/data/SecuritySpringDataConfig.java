package com.danghieu99.monolith.security.config.data;

import com.danghieu99.monolith.security.config.auth.AuthTokenProperties;
import com.danghieu99.monolith.security.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({AuthTokenProperties.class})
@EnableJpaRepositories(basePackages = {"com.danghieu99.monolith.security.repository.jpa"})
@EnableRedisRepositories(basePackages = {"com.danghieu99.monolith.security.repository.redis"})
public class SecuritySpringDataConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisTemplate<String, Token> redisTokenTemplate() {
        RedisTemplate<String, Token> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}