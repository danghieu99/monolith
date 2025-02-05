package com.danghieu99.monolith.product.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {

    private final String name;

    private final String description;

    private final String imgUrl;
}
