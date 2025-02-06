package com.danghieu99.monolith.cart.mapper;

import com.danghieu99.monolith.cart.dto.CartItemDto;
import com.danghieu99.monolith.cart.entity.CartItem;
import com.danghieu99.monolith.cart.entity.SaveCartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItem toCartItem(CartItemDto cartItemDto);

    CartItem toCartItem(SaveCartItem saveCartItem);

    CartItemDto toCartItemDto(CartItem cartItem);

    CartItemDto toCartItemDto(SaveCartItem saveCartItem);

    SaveCartItem toSavedCartItem(CartItemDto cartItemDto);

    SaveCartItem toSavedCartItem(CartItem cartItem);

}
