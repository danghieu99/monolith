package com.danghieu99.monolith.auth.config;

import com.danghieu99.monolith.auth.config.authentication.TokenProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableConfigurationProperties({TokenProperties.class})
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = {"com.danghieu99.monolith.auth.repository"})
@EnableRedisRepositories(basePackages = {"com.danghieu99.monolith.auth.redisrepository"})
public class SpringDataConfig {
}