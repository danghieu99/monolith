package com.danghieu99.monolith.order.mapper;

import com.danghieu99.monolith.order.dto.request.kafka.CancelOrderKafkaRequest;
import com.danghieu99.monolith.order.dto.request.CancelOrderRequest;
import com.danghieu99.monolith.order.dto.request.kafka.PlaceOrderKafkaRequest;
import com.danghieu99.monolith.order.dto.request.PlaceOrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface OrderMapper {

    PlaceOrderKafkaRequest toKafkaPlaceOrderRequest(PlaceOrderRequest request);

    CancelOrderKafkaRequest toKafkaCancelOrderRequest(CancelOrderRequest request);
}
