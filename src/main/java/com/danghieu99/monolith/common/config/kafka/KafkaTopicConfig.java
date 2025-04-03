package com.danghieu99.monolith.common.config.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topics.order.place}")
    private String placeOrderTopicName;

    @Value("${spring.kafka.topics.order.cancel}")
    private String cancelOrderTopicName;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic placeOrderTopic() {
        return TopicBuilder.name(placeOrderTopicName)
                .partitions(4)
                .replicas(2)
                .compact()
                .build();
    }

    @Bean
    public NewTopic cancelOrderTopic() {
        return TopicBuilder.name(cancelOrderTopicName)
                .partitions(4)
                .replicas(2)
                .compact()
                .build();
    }
}
