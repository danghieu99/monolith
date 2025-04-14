package com.danghieu99.monolith.cart.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@RedisHash("carts")
@Builder
public class Cart {

    @Id
    private String accountUUID;

    //variantUUID - quantity
    private Map<String, Integer> items;
}