package com.danghieu99.monolith.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceOrderRequest {

    @NotBlank
    PlaceOrderRequestAddress address;
}
