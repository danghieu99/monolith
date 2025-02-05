package com.danghieu99.monolith.cart.entity.value;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SavedCartItem {

    @NotNull
    private UUID productId;

    @NotNull
    private UUID variantId;

    @NotNull
    private int quantity;
}
