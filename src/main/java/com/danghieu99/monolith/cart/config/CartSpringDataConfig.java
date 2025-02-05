package com.danghieu99.monolith.cart.config;

import com.danghieu99.monolith.cart.entity. CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@RequiredArgsConstructor
@EnableRedisRepositories(basePackages = {"com.danghieu99.monolith.cart.repository.redis"})
@EnableJpaRepositories(basePackages = {"com.danghieu99.monolith.cart.repository.jpa"})
public class CartSpringDataConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisTemplate<String, CartItem> redisCartTemplate() {
        RedisTemplate<String, CartItem> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}