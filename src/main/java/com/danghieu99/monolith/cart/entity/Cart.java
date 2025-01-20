package com.danghieu99.monolith.cart.entity;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@Data
@Builder
@RedisHash("carts")
public class Cart {



}
