package com.danghieu99.monolith.order.dto.request.kafka;

import com.danghieu99.monolith.order.dto.request.OrderAddressRequest;
import com.danghieu99.monolith.order.dto.request.OrderItemRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlaceOrderKafkaRequest {

    @NotEmpty
    private final String shopUUID;

    @NotNull
    private final OrderAddressRequest address;

    @NotNull
    private final String shipmentProviderUUID;

    @NotEmpty
    private final List<OrderItemRequest> items;

    @NotEmpty
    private String accountUUID;
}
