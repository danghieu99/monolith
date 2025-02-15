package com.danghieu99.monolith.product.service.product.user;

import com.danghieu99.monolith.product.dto.response.CategoryResponse;
import com.danghieu99.monolith.product.mapper.CategoryMapper;
import com.danghieu99.monolith.product.service.product.daoservice.CategoryService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCategoryService {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public Page<CategoryResponse> getAll(@NotNull final Pageable pageable) {
        return categoryService.getAll(pageable).map(categoryMapper::toResponse);
    }

    public CategoryResponse getByUUID(@NotNull final String uuid, @NotNull final Pageable pageable) {
        return categoryMapper.toResponse(categoryService.getByUUID(UUID.fromString(uuid)));
    }

    public Page<CategoryResponse> getBySuperCategoryUUID(@NotNull final String uuid, @NotNull final Pageable pageable) {
        return categoryService.getBySuperCategoryUUID(UUID.fromString(uuid), pageable).map(categoryMapper::toResponse);
    }

    public Page<CategoryResponse> getBySubCategoryUUID(@NotNull final String uuid, @NotNull final Pageable pageable) {
        return categoryService.getBySubCategoryUUID(UUID.fromString(uuid), pageable).map(categoryMapper::toResponse);
    }

    public Page<CategoryResponse> getByNameContaining(@NotNull final String name, @NotNull final Pageable pageable) {
        return categoryService.getByNameContaining(name, pageable).map(categoryMapper::toResponse);
    }
}
