package com.danghieu99.monolith.cart.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "saved_cart_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SaveCartItem extends BaseEntity {

    @Column(nullable = false, unique = true)
    @NotNull
    private int userId;

    @NotNull
    private UUID productId;

    @NotNull
    private UUID variantId;

    @NotNull
    private int quantity;

}
