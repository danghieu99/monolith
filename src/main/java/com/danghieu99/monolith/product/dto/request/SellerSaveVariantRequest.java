package com.danghieu99.monolith.product.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class SellerSaveVariantRequest {

    @NotEmpty
    private Map<String, String> attributes;

    private BigDecimal price;

    private int stock;
}
