package com.danghieu99.monolith.ecommerce.order.kafka.producer;

import com.danghieu99.monolith.ecommerce.order.dto.kafka.RequestCancelOrderKafkaMessage;
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
public class RequestCancelOrderKafkaProducer {

    @Value("${spring.kafka.topics.order.request.cancel}")
    private String topic;

    @Value("${system.code.order}")
    private String systemCode;

    private final KafkaTemplate<String, RequestCancelOrderKafkaMessage> kafkaTemplate;

    @Async
    public void send(final RequestCancelOrderKafkaMessage request) {
        if (Objects.nonNull(request)) {
            request.setSystemCode(systemCode);
            CompletableFuture<SendResult<String, RequestCancelOrderKafkaMessage>> future = kafkaTemplate.send(topic, request);
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
