package com.danghieu99.monolith.order.service;

import com.danghieu99.monolith.order.dto.request.kafka.OrderCancelEvenKafkaRequest;
import com.danghieu99.monolith.order.dto.request.CancelOrderRequest;
import com.danghieu99.monolith.order.dto.request.kafka.PlaceOrderEventKafkaRequest;
import com.danghieu99.monolith.order.dto.request.PlaceOrderRequest;
import com.danghieu99.monolith.order.dto.response.OrderDetailsResponse;
import com.danghieu99.monolith.order.entity.Cancel;
import com.danghieu99.monolith.order.kafka.CancelOrderKafkaProducer;
import com.danghieu99.monolith.order.kafka.PlaceOrderKafkaProducer;
import com.danghieu99.monolith.order.mapper.OrderMapper;
import com.danghieu99.monolith.order.repository.CancelRepository;
import com.danghieu99.monolith.order.repository.OrderItemRepository;
import com.danghieu99.monolith.order.repository.OrderRepository;
import com.danghieu99.monolith.security.config.auth.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final PlaceOrderKafkaProducer placeOrderKafkaProducer;
    private final CancelOrderKafkaProducer cancelOrderKafkaProducer;
    private final OrderItemRepository orderItemRepository;
    private final CancelRepository cancelRepository;

    public List<OrderDetailsResponse> getAllByCurrentUser(UserDetailsImpl userDetails) {
        return orderRepository.findByUserAccountUUID(userDetails.getUuid())
                .stream()
                .map(order -> {
                    OrderDetailsResponse orderDetails = orderMapper.toOrderDetailsResponse(order);
                    orderDetails.setItems(orderItemRepository.findByOrderId(order.getId())
                            .stream()
                            .map(orderMapper::toOrderItemResponse)
                            .toList());
                    return orderDetails;
                })
                .toList();
    }

    @Async
    @Transactional
    public void place(final PlaceOrderRequest request,
                      final UserDetailsImpl userDetails) {
        PlaceOrderEventKafkaRequest kafkaRequest = orderMapper.toKafkaPlaceOrderRequest(request);
        kafkaRequest.setAccountUUID(userDetails.getUuid());
//        placeOrderKafkaProducer.send(kafkaRequest);
    }

    @Async
    @Transactional
    public void cancel(CancelOrderRequest request, UserDetailsImpl userDetails) {
        OrderCancelEvenKafkaRequest kafkaRequest = orderMapper.toKafkaCancelOrderRequest(request);
        kafkaRequest.setAccountUUID(userDetails.getUuid());
//        cancelOrderKafkaProducer.send(kafkaRequest);

        Cancel cancel = Cancel.builder()
                .userAccountUUID(userDetails.getUuid())
                .orderUUID(request.getOrderUUID())
                .shopUUID(request.getShopUUID())
                .build();
        cancelRepository.save(cancel);
    }
}