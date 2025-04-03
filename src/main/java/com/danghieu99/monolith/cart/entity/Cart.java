package com.danghieu99.monolith.cart.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@RedisHash("carts")
@Builder
public class Cart {

    @Id
    private String id;

    @NotEmpty
    private String userUUID;


}
