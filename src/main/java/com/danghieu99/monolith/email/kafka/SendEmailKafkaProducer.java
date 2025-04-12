package com.danghieu99.monolith.email.kafka;

import com.danghieu99.monolith.email.dto.kafka.SendEmailKafkaRequest;
import com.danghieu99.monolith.email.service.SendEmailService;
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
public class SendEmailKafkaProducer {

    private final SendEmailService sendEmailService;

    @Value("${spring.kafka.topics.email.send}")
    private String topic;

    private KafkaTemplate<String, SendEmailKafkaRequest> kafkaTemplate;

    @Async
    public void send(SendEmailKafkaRequest request) {
        if (Objects.nonNull(request)) {
            CompletableFuture<SendResult<String, SendEmailKafkaRequest>> future = kafkaTemplate.send(topic, request);
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