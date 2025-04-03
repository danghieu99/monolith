package com.danghieu99.monolith.order.kafka;

import com.danghieu99.monolith.order.dto.request.kafka.CancelOrderKafkaRequest;
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
public class CancelOrderKafkaProducer {

    @Value("${spring.kafka.topics.order.cancel}")
    private String topic;

    private final KafkaTemplate<String, CancelOrderKafkaRequest> kafkaTemplate;

    @Async
    public void send(final CancelOrderKafkaRequest request) {
        if (Objects.nonNull(request)) {
            CompletableFuture<SendResult<String, CancelOrderKafkaRequest>> future = kafkaTemplate.send(topic, request);
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
