package com.danghieu99.monolith.cart.mapper;

import com.danghieu99.monolith.cart.dto.CartItemDto;
import com.danghieu99.monolith.cart.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItem toCartItem(CartItemDto cartItemDto);

    CartItemDto toCartItemDto(CartItem cartItem);
}
