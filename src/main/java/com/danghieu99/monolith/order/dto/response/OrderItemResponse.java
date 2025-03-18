package com.danghieu99.monolith.order.dto.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {
    @NotEmpty
    private final String productUUID;

    @NotEmpty
    private final String variantUUID;

    @NotNull
    private final int quantity;
}
