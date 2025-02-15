package com.danghieu99.monolith.product.service.product.admin;

import com.danghieu99.monolith.product.dto.request.SaveCategoryRequest;
import com.danghieu99.monolith.product.dto.request.UpdateCategoryRequest;
import com.danghieu99.monolith.product.dto.response.CategoryResponse;
import com.danghieu99.monolith.product.entity.Category;
import com.danghieu99.monolith.product.mapper.CategoryMapper;
import com.danghieu99.monolith.product.service.product.daoservice.CategoryService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryService {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Transactional
    public Category create(@NotNull final SaveCategoryRequest request) {
        return categoryService.create(categoryMapper.toCategory(request));
    }

    @Transactional
    public void deleteById(@NotNull int id) {
        categoryService.deleteById(id);
    }

    public CategoryResponse getById(@NotNull int id) {
        return categoryMapper.toResponse(categoryService.getById(id));
    }

    @Transactional
    public CategoryResponse updateById(@NotNull final UpdateCategoryRequest request) {
        Category current = categoryService.getById(request.getId());
        if (request.getName() != null) {
            current.setName(request.getName());
        }
        if (request.getDescription() != null) {
            current.setDescription(request.getDescription());
        }
        return categoryMapper.toResponse(categoryService.update(current));
    }
}
