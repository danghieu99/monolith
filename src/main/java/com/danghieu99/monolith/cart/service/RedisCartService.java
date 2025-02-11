package com.danghieu99.monolith.cart.service;

import com.danghieu99.monolith.cart.entity.CartItem;
import com.danghieu99.monolith.cart.repository.redis.CartItemRepository;
import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisCartService {

    private final CartItemRepository cartItemRepository;

    public CartItem create(CartItem cartItem) {
        if (cartItem.getId() != null) {
            throw new IllegalArgumentException("New CartItem id must be null");
        }
        return cartItemRepository.save(cartItem);
    }

    public CartItem update(CartItem cartItem) {
        if (cartItem.getId() == null) {
            throw new IllegalArgumentException("Update CartItem id must not be null");
        }
        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> getByUserId(int userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public void deleteByUserId(int userId) {
        cartItemRepository.deleteByUserId(userId);
    }

    public CartItem getById(String id) {
        return cartItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("CartItem", "id", id));
    }

    public void deleteById(String id) {
        cartItemRepository.deleteById(id);
    }
}
