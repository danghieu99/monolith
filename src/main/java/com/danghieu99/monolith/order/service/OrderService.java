package com.danghieu99.monolith.order.service;

import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.order.constant.EOrderStatus;
import com.danghieu99.monolith.order.dto.request.kafka.OrderCancelEvenKafkaRequest;
import com.danghieu99.monolith.order.dto.request.kafka.PlaceOrderEventKafkaRequest;
import com.danghieu99.monolith.order.entity.OrderItem;
import com.danghieu99.monolith.order.entity.Order;
import com.danghieu99.monolith.order.repository.OrderItemRepository;
import com.danghieu99.monolith.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Async
    @Transactional
    public void save(@NotNull @Valid PlaceOrderEventKafkaRequest request) {
        List<OrderItem> orderItems = new ArrayList<>();
        Order newOrder = Order.builder()
                .shopUUID(request.getShopUUID())
                .userAccountUUID(request.getAccountUUID())
                .build();
        Order savedOrder = orderRepository.save(newOrder);

        request.getItems().forEach(requestItem -> {
            OrderItem newOrderItem = OrderItem.builder()
                    .orderId(savedOrder.getId())
                    .variantUUID(UUID.fromString(requestItem.getVariantUUID()))
                    .quantity(requestItem.getQuantity())
                    .build();
            orderItems.add(newOrderItem);
        });
        orderItemRepository.saveAll(orderItems);
    }

    @Transactional
    public void cancel(@NotNull @Valid OrderCancelEvenKafkaRequest request) {
        Order savedOrder = orderRepository.findByUuid(UUID.fromString(request.getOrderUUID()))
                .orElseThrow(() -> new ResourceNotFoundException("Order", "uuid", request.getOrderUUID()));
        savedOrder.setStatus(EOrderStatus.ORDER_CANCELED);
        orderRepository.save(savedOrder);
    }

    @Async
    @Transactional
    public void updateStatus(String orderUUID, String status) {
        EOrderStatus orderStatus = EOrderStatus.valueOf(status);
        orderRepository.updateOrderStatus(UUID.fromString(orderUUID), orderStatus);
    }
}
