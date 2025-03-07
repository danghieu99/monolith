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
    private final List<PlaceOrderItem> items;

    @NotNull
    private final PlaceOrderAddress address;

    @NotNull
    private UUID shipmentProviderUUID;
}
