package com.danghieu99.monolith.cart.entity;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@RedisHash("cart_item")
public class CartItem {

    @Id
    private String id;

    @NotNull
    private Integer userId;

    @NotNull
    private UUID productId;

    @NotNull
    private UUID variantId;

    @NotNull
    private int quantity;
}