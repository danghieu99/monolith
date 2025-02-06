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
public class UserCartService {

    private final RedisCartService redisCartService;

    private final AuthenticationService authenticationService;

    private final CartItemMapper cartItemMapper;

    private final SaveCartService saveCartService;

    public CartDto getCurrentUserCart() {
        int userId = authenticationService.getCurrentUserDetails().getId();
        List<CartItem> items;
        items = redisCartService.getByUserId(userId);
        if (!items.isEmpty()) {
            var savedItems = saveCartService.getByUserId(userId).stream().map(cartItemMapper::toCartItem).toList();
            if (!savedItems.isEmpty()) {
                savedItems.stream().parallel().forEach(redisCartService::create);
            }
        }
        var itemsDto = items.stream().map(cartItemMapper::toCartItemDto).toList();
        return CartDto.builder()
                .items(itemsDto)
                .build();
    }

    @Transactional
    public void clearCurrentUserCart() {
        redisCartService.deleteByUserId(authenticationService.getCurrentUserDetails().getId());
    }

    @Transactional
    public CartDto addCartItem(CartItemDto cartItemDto) {
        redisCartService.create(cartItemMapper.toCartItem(cartItemDto));
        return getCurrentUserCart();
    }

    @Transactional
    public CartDto updateCartItem(CartItemDto cartItemDto) {
        redisCartService.update(cartItemMapper.toCartItem(cartItemDto));
        return getCurrentUserCart();
    }

    @Transactional
    public CartDto deleteCartItem(String id) {
        redisCartService.deleteById(id);
        return getCurrentUserCart();
    }

    public void saveCurrentUserCartItems() {
        int userId = authenticationService.getCurrentUserDetails().getId();
        List<CartItem> items = redisCartService.getByUserId(userId);
        if (!items.isEmpty()) {
            items.parallelStream().map(cartItemMapper::toSavedCartItem).forEach(saveCartService::save);
        }
    }
}