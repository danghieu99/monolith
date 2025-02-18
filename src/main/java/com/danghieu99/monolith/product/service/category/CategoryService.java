package com.danghieu99.monolith.product.service.category;

import com.danghieu99.monolith.product.dto.response.CategoryResponse;
import com.danghieu99.monolith.product.mapper.CategoryMapper;
import com.danghieu99.monolith.product.service.dao.CategoryDaoService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryDaoService categoryDaoService;
    private final CategoryMapper categoryMapper;

    public Page<CategoryResponse> getAll(@NotNull final Pageable pageable) {
        return categoryDaoService.getAll(pageable).map(categoryMapper::toResponse);
    }

    public CategoryResponse getByUUID(@NotNull final String uuid) {
        return categoryMapper.toResponse(categoryDaoService.getByUUID(UUID.fromString(uuid)));
    }

    public Page<CategoryResponse> getBySuperCategoryUUID(@NotNull final String uuid, @NotNull final Pageable pageable) {
        return categoryDaoService.getBySuperCategoryUUID(UUID.fromString(uuid), pageable).map(categoryMapper::toResponse);
    }

    public Page<CategoryResponse> getBySubCategoryUUID(@NotNull final String uuid, @NotNull final Pageable pageable) {
        return categoryDaoService.getBySubCategoryUUID(UUID.fromString(uuid), pageable).map(categoryMapper::toResponse);
    }

    public Page<CategoryResponse> getByNameContaining(@NotNull final String name, @NotNull final Pageable pageable) {
        return categoryDaoService.getByNameContaining(name, pageable).map(categoryMapper::toResponse);
    }
}
