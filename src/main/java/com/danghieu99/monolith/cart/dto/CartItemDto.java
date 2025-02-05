package com.danghieu99.monolith.cart.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class CartItemDto {

    @NotNull
    private String id;

    @NotNull
    private final UUID variantUUID;

    @NotNull
    private final Map<String, String> attributes;

    @NotNull
    private final BigDecimal price;
}
