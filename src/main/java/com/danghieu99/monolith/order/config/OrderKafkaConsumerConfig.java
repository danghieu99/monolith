package com.danghieu99.monolith.order.config;

import com.danghieu99.monolith.common.kafka.KafkaConsumerConfig;
import com.danghieu99.monolith.order.dto.request.kafka.CancelOrderKafkaRequest;
import com.danghieu99.monolith.order.dto.request.kafka.PlaceOrderKafkaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@Configuration
@RequiredArgsConstructor
public class OrderKafkaConsumerConfig {

    private final KafkaConsumerConfig kafkaConsumerConfig;

    @Bean("placeOrderListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, PlaceOrderKafkaRequest> placeOrderListenerFactory() {
        return kafkaConsumerConfig.objConcurrentKafkaListenerContainerFactory();
    }

    @Bean("cancelOrderListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, CancelOrderKafkaRequest> cancelOrderListenerFactory() {
        return kafkaConsumerConfig.objConcurrentKafkaListenerContainerFactory();
    }
}
