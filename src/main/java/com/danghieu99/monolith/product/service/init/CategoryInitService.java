package com.danghieu99.monolith.product.service.init;

import com.danghieu99.monolith.product.entity.Category;
import com.danghieu99.monolith.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CategoryInitService {

    private final CategoryService categoryService;

    public void init() {
        if (categoryService.getAll().isEmpty()) {
            IntStream.range(1, 50).parallel().forEach(i -> {
                categoryService.create(Category.builder()
                        .name("Default Category" + i)
                        .description("Default category description" + i)
                        .build());
            });
        }
    }
}
