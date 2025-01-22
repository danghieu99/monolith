package com.danghieu99.monolith.cart.entity;

import com.danghieu99.monolith.cart.entity.value.CartItem;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Data
@Builder
@RedisHash("carts")
public class Cart {

    private int userId;

    private List<CartItem> items;

}
