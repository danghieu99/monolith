package com.danghieu99.monolith.product.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductMinioConfig {

    @Value("${minio.endpoint}")
    private String minioEndpoint;

    @Value("${minio.port}")
    private int minioPort;

    @Value("${minio.access-key}")
    private String minioAccessKey;

    @Value("${minio.secret-key}")
    private String minioSecretKey;

    @Bean
    public MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(minioEndpoint, minioPort, false)
                .credentials(minioAccessKey, minioSecretKey)
                .build();
    }
}