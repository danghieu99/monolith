package com.danghieu99.monolith.order.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlaceOrderRequest {

    @NotEmpty
    private final String shopUUID;

    @NotNull
    private final OrderAddressRequest address;

    @NotNull
    private String shipmentProviderUUID;

    @NotEmpty
    private final List<OrderItemRequest> items;
}