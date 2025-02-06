package com.danghieu99.monolith.cart.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartDto {

    private final List<CartItemDto> items;

}
