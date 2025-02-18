package com.danghieu99.monolith.product.service.init;

import com.danghieu99.monolith.product.entity.Category;
import com.danghieu99.monolith.product.service.dao.CategoryDaoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CategoryInitService {

    private final CategoryDaoService categoryDaoService;

    @Transactional
    public void init() {
        if (categoryDaoService.getAll().isEmpty()) {
            IntStream.range(1, 50).parallel().forEach(i -> {
                categoryDaoService.save(Category.builder()
                        .name("Default Category " + i)
                        .description("Default category description " + i)
                        .build());
            });
        }
    }
}