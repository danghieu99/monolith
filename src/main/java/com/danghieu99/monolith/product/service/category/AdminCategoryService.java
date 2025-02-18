package com.danghieu99.monolith.product.service.category;

import com.danghieu99.monolith.product.dto.request.SaveCategoryRequest;
import com.danghieu99.monolith.product.dto.response.CategoryResponse;
import com.danghieu99.monolith.product.entity.Category;
import com.danghieu99.monolith.product.mapper.CategoryMapper;
import com.danghieu99.monolith.product.service.dao.CategoryDaoService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryService {

    private final CategoryDaoService categoryDaoService;
    private final CategoryMapper categoryMapper;

    @Transactional
    public Category save(@NotNull final SaveCategoryRequest request) {
        return categoryDaoService.save(categoryMapper.toCategory(request));
    }

    @Transactional
    public void deleteById(@NotNull int id) {
        categoryDaoService.deleteById(id);
    }

    public CategoryResponse getById(@NotNull int id) {
        return categoryMapper.toResponse(categoryDaoService.getById(id));
    }

    @Transactional
    public CategoryResponse updateById(@NotNull final int id, @NotNull final SaveCategoryRequest request) {
        Category current = categoryDaoService.getById(id);
        if (request.getName() != null) {
            current.setName(request.getName());
        }
        if (request.getDescription() != null) {
            current.setDescription(request.getDescription());
        }
        return categoryMapper.toResponse(categoryDaoService.update(current));
    }
}
