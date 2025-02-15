package com.danghieu99.monolith.product.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCategoryRequest {

    @NotNull
    private final int id;

    private final String name;

    private final String description;
}
