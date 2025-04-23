package com.danghieu99.monolith.order.mapper;

import com.danghieu99.monolith.order.dto.request.kafka.OrderCancelEvenKafkaRequest;
import com.danghieu99.monolith.order.dto.request.CancelOrderRequest;
import com.danghieu99.monolith.order.dto.request.kafka.PlaceOrderEventKafkaRequest;
import com.danghieu99.monolith.order.dto.request.PlaceOrderRequest;
import com.danghieu99.monolith.order.dto.response.OrderDetailsResponse;
import com.danghieu99.monolith.order.dto.response.OrderItemResponse;
import com.danghieu99.monolith.order.entity.Order;
import com.danghieu99.monolith.order.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface OrderMapper {

    PlaceOrderEventKafkaRequest toKafkaPlaceOrderRequest(PlaceOrderRequest request);

    OrderCancelEvenKafkaRequest toKafkaCancelOrderRequest(CancelOrderRequest request);

    OrderDetailsResponse toOrderDetailsResponse(Order order);

    OrderItemResponse toOrderItemResponse(OrderItem item);
}
