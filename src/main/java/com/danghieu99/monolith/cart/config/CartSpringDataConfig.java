package com.danghieu99.monolith.cart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(basePackages = {"com.danghieu99.monolith.cart.repository.redis"})
@EnableJpaRepositories(basePackages = {"com.danghieu99.monolith.cart.repository.jpa"})
public class CartSpringDataConfig {
}