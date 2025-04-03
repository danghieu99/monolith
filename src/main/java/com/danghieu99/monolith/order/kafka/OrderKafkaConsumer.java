package com.danghieu99.monolith.order.kafka;

import com.danghieu99.monolith.order.dto.request.kafka.CancelOrderKafkaRequest;
import com.danghieu99.monolith.order.dto.request.kafka.PlaceOrderKafkaRequest;
import com.danghieu99.monolith.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderKafkaConsumer {

    private final OrderService orderService;

    @KafkaListener(topics = "${spring.kafka.topics.order.cancel}", groupId = "group-order-cancel-worker", concurrency = "4")
    public void listenCancelOrderTopic(CancelOrderKafkaRequest request) {
        orderService.cancel(request);
    }

    @KafkaListener(topics = "${spring.kafka.topics.order.place}", groupId = "group-order-place-worker", concurrency = "4")
    public void listenPlaceOrderTopic(PlaceOrderKafkaRequest request) {
        orderService.save(request);
    }
}
