package com.danghieu99.monolith.product.dto.response;

import com.danghieu99.monolith.product.entity.*;
import lombok.Builder;
import lombok.Data;

import java.util.*;

@Data
@Builder
public class GetProductDetailsResponse {

    private UUID uuid;

    private String name;

    private String description;

    private Set<Category> categories;

    private Shop shop;

    private Map<String, String> attributes;

    private Set<Variant> variants;
}
