package com.danghieu99.monolith.cart.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CartDto {

    @NotNull
    private final List<CartItemDto> items;

    //    @NotNull
    private final BigDecimal totalPrice;
}
