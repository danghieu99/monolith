package com.danghieu99.monolith.cart.service;

import com.danghieu99.monolith.cart.dto.GetCartResponse;
import com.danghieu99.monolith.cart.entity.Cart;
import com.danghieu99.monolith.cart.mapper.CartMapper;
import com.danghieu99.monolith.cart.repository.CartRepository;
import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.security.config.auth.UserDetailsImpl;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public GetCartResponse getCart(@NotNull UserDetailsImpl userDetails) {
        Cart cart = cartRepository.findByAccountUUID(userDetails.getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "accountUUID", userDetails.getUuid()));
        return cartMapper.toGetCartResponse(cart);
    }

    @Async
    public void updateCartItem(@NotNull final UserDetailsImpl userDetails,
                               @NotBlank final String variantUUID,
                               @NotNull final int quantity) {
        Cart cart = cartRepository.findByAccountUUID(userDetails.getUuid())
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "accountUUID", userDetails.getUuid()));
        Map<String, Integer> items = new HashMap<>(cart.getItems());
        if (quantity <= 0) {
            items.remove(variantUUID);
        } else {
            items.put(variantUUID, quantity);
        }
        cart.setItems(new HashMap<>(items));
        cartRepository.save(cart);
    }
}
