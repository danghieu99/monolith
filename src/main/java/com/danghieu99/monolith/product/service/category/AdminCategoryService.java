package com.danghieu99.monolith.product.service.category;

import com.danghieu99.monolith.product.entity.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryService {

    private final CategoryCrudService categoryService;

    public Category save(final Category category) {
        return categoryService.create(category);
    }

    public Category update(final Category category) {
        return categoryService.update(category);
    }

    public void deleteById(int id) {
        categoryService.deleteById(id);
    }
}
