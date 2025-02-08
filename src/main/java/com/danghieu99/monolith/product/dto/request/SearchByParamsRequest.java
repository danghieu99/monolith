package com.danghieu99.monolith.product.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class SearchByParamsRequest {

    private String name;

    private Set<String> categories;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;
}
