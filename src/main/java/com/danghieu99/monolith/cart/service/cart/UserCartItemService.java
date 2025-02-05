package com.danghieu99.monolith.cart.service.cart;

import com.danghieu99.monolith.cart.dto.CartDto;
import com.danghieu99.monolith.cart.entity.CartItem;
import com.danghieu99.monolith.security.service.auth.AuthenticationService;
import com.danghieu99.monolith.cart.mapper.CartItemMapper;
import com.danghieu99.monolith.cart.dto.CartItemDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCartItemService {

    private final CartItemCrudService cartItemCrudService;

    private final AuthenticationService authenticationService;

    private final CartItemMapper cartItemMapper;

    public CartDto getCurrentUserCartItems() {
        List<CartItem> items = cartItemCrudService.getByUserId(authenticationService.getCurrentUserDetails().getId());
        return CartDto.builder()
                //totalPrice
                .items(items.stream().map(cartItemMapper::toCartItemDto).toList())
                .build();
    }

    @Transactional
    public void deleteCurrentUserCartItems() {
        cartItemCrudService.deleteByUserId(authenticationService.getCurrentUserDetails().getId());
    }

    @Transactional
    public CartDto addCartItem(CartItemDto cartItemDto) {
        cartItemCrudService.create(cartItemMapper.toCartItem(cartItemDto));
        return getCurrentUserCartItems();
    }

    @Transactional
    public CartDto updateCartItem(CartItemDto cartItemDto) {
        cartItemCrudService.update(cartItemMapper.toCartItem(cartItemDto));
        return getCurrentUserCartItems();
    }

    @Transactional
    public CartDto deleteCartItem(String id) {
        cartItemCrudService.deleteById(id);
        return getCurrentUserCartItems();
    }
}
