package com.danghieu99.monolith.product.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class VariantDto {

    @NotEmpty
    private final Map<String, String> attributes;

    @NotNull
    private int stock;

    @NotNull
    private BigDecimal price;
}
