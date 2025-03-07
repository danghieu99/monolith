package com.danghieu99.monolith.product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@EnableJpaRepositories(basePackages = "com.danghieu99.monolith.product.repository.jpa")
public class ProductSpringConfig {

}