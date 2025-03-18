package com.danghieu99.monolith.order.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class PlaceOrderRequest {

    @NotEmpty
    private final String shopUUID;

    @NotEmpty
    private final List<OrderItemRequest> items;

    @NotNull
    private final OrderAddressRequest address;

    @NotNull
    private UUID shipmentProviderUUID;
}