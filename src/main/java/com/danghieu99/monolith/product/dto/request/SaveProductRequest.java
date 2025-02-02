package com.danghieu99.monolith.product.dto.request;

import com.danghieu99.monolith.product.dto.VariantDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class SaveProductRequest {

    @NotEmpty
    private final String name;

    @NotEmpty
    private final String description;

    @NotEmpty
    private final String shopUUID;

    @NotEmpty
    private final Set<String> categories;

    @NotNull
    private final BigDecimal price;

    @NotEmpty
    private final Set<VariantDto> variants;
}
