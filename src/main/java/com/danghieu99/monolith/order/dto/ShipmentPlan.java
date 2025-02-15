package com.danghieu99.monolith.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ShipmentPlan {

    @NotNull
    private PlaceOrderRequestAddress placeOrderRequestAddress;

    @NotNull
    private UUID shipmentProviderUUID;
}
