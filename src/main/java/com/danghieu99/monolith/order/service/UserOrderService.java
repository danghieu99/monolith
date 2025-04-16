package com.danghieu99.monolith.order.service;

import com.danghieu99.monolith.order.dto.request.kafka.CancelOrderKafkaRequest;
import com.danghieu99.monolith.order.dto.request.CancelOrderRequest;
import com.danghieu99.monolith.order.dto.request.kafka.PlaceOrderKafkaRequest;
import com.danghieu99.monolith.order.dto.request.PlaceOrderRequest;
import com.danghieu99.monolith.order.dto.response.OrderDetailsResponse;
import com.danghieu99.monolith.order.kafka.CancelOrderKafkaProducer;
import com.danghieu99.monolith.order.kafka.PlaceOrderKafkaProducer;
import com.danghieu99.monolith.order.mapper.OrderMapper;
import com.danghieu99.monolith.order.repository.OrderItemRepository;
import com.danghieu99.monolith.order.repository.OrderRepository;
import com.danghieu99.monolith.security.config.auth.UserDetailsImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final PlaceOrderKafkaProducer placeOrderKafkaProducer;
    private final CancelOrderKafkaProducer cancelOrderKafkaProducer;
    private final OrderItemRepository orderItemRepository;

    public List<String> getUUIDsByCurrentUser(@NotNull UserDetailsImpl userDetails) {
        return orderRepository.findByUserAccountUUID(userDetails.getUuid())
                .stream()
                .map(order -> order.getUuid().toString())
                .toList();
    }

    public List<OrderDetailsResponse> getAllByCurrentUser(@NotNull UserDetailsImpl userDetails) {
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
    public void place(@RequestBody final PlaceOrderRequest request, final UserDetailsImpl userDetails) {
        PlaceOrderKafkaRequest kafkaRequest = orderMapper.toKafkaPlaceOrderRequest(request);
        kafkaRequest.setAccountUUID(userDetails.getUuid());
        placeOrderKafkaProducer.send(kafkaRequest);
    }

    @Async
    @Transactional
    public void cancel(CancelOrderRequest request, UserDetailsImpl userDetails) {
        CancelOrderKafkaRequest kafkaRequest = orderMapper.toKafkaCancelOrderRequest(request);
        kafkaRequest.setAccountUUID(userDetails.getUuid());
        cancelOrderKafkaProducer.send(kafkaRequest);
    }
}