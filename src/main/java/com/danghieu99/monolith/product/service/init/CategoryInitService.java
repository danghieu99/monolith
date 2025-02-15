package com.danghieu99.monolith.product.service.init;

import com.danghieu99.monolith.product.entity.Category;
import com.danghieu99.monolith.product.service.product.daoservice.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CategoryInitService {

    private final CategoryService categoryService;

    @Transactional
    public void init() {
        if (categoryService.getAll().isEmpty()) {
            IntStream.range(1, 50).parallel().forEach(i -> {
                categoryService.create(Category.builder()
                        .name("Default Category " + i)
                        .description("Default category description " + i)
                        .build());
            });
        }
    }
}