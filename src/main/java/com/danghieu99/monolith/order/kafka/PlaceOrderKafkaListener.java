package com.danghieu99.monolith.order.kafka;

import com.danghieu99.monolith.order.dto.request.kafka.PlaceOrderKafkaRequest;
import com.danghieu99.monolith.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceOrderKafkaListener {

    private final OrderService orderService;

    @KafkaListener(topics = "${spring.kafka.topics.order.place}",
            groupId = "group-order-place-worker",
            concurrency = "4",
            containerFactory = "placeOrderListenerFactory")
    public void listen(PlaceOrderKafkaRequest request) {
        orderService.save(request);
    }
}