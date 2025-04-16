package com.danghieu99.monolith.order.kafka;

import com.danghieu99.monolith.order.dto.request.kafka.CancelOrderKafkaRequest;
import com.danghieu99.monolith.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CancelOrderKafkaListener {

    private final OrderService orderService;

    @KafkaListener(topics = "${spring.kafka.topics.order.cancel}",
            groupId = "group-order-cancel-worker",
            concurrency = "4",
            containerFactory = "cancelOrderListenerFactory")
    public void listen(CancelOrderKafkaRequest request) {
        orderService.cancel(request);
    }
}
