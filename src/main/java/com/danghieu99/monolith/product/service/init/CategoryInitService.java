package com.danghieu99.monolith.product.service.init;

import com.danghieu99.monolith.product.entity.Category;
import com.danghieu99.monolith.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryInitService {

    private final CategoryService categoryService;

    public void init() {
        categoryService.create(Category.builder()
                .name("Default Category")
                .description("Default category description")
                .build());
    }
}
