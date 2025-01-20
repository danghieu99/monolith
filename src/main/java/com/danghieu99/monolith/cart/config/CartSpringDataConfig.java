package com.danghieu99.monolith.cart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(basePackages = {"com.danghieu99.monolith.cart.repository.redis"})
public class CartSpringDataConfig {
}