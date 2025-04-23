package com.danghieu99.monolith.order.kafka;

import com.danghieu99.monolith.order.dto.request.kafka.PlaceOrderEventKafkaRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceOrderKafkaProducer {

    @Value("${spring.kafka.topics.order.place}")
    private String topic;

    @Value("${system.code.order}")
    private String systemCode;

    private final KafkaTemplate<String, PlaceOrderEventKafkaRequest> kafkaTemplate;

    @Async
    public void send(final PlaceOrderEventKafkaRequest request) {
        if (Objects.nonNull(request)) {
            request.setSystemCode(systemCode);
            CompletableFuture<SendResult<String, PlaceOrderEventKafkaRequest>> future = kafkaTemplate.send(topic, request);
            future.thenAccept(sendResult -> {
                        log.info("Message [{}] delivered with offset {}", request, sendResult.getRecordMetadata().offset());
                    })
                    .exceptionally(ex -> {
                        log.error("Message [{}] failed to deliver", request, ex);
                        return null;
                    });
        }
    }
}
