package com.danghieu99.monolith.product.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UpdateProductDetailsRequest {

    @NotEmpty
    private final String name;

    @NotEmpty
    private final String description;
}
