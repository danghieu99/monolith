package com.danghieu99.monolith.cart.repository.redis;

import com.danghieu99.monolith.cart.entity.CartItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemRepository extends CrudRepository<CartItem, String> {

    List<CartItem> findByUserId(int userId);

    void deleteByUserId(int userId);
}