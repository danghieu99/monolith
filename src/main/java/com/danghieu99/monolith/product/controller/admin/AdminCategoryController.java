package com.danghieu99.monolith.product.controller.admin;

import com.danghieu99.monolith.product.dto.request.SaveCategoryRequest;
import com.danghieu99.monolith.product.dto.request.UpdateCategoryRequest;
import com.danghieu99.monolith.product.dto.response.CategoryResponse;
import com.danghieu99.monolith.product.mapper.CategoryMapper;
import com.danghieu99.monolith.product.service.product.admin.AdminCategoryService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping("")
    public CategoryResponse create(@NotNull @RequestParam SaveCategoryRequest category) {
        return categoryMapper.toResponse(adminCategoryService.create(category));
    }

    public void deleteById(@NotNull int id) {
        adminCategoryService.deleteById(id);
    }

    public CategoryResponse getById(@NotNull int id) {
        return adminCategoryService.getById(id);
    }

    @PatchMapping("")
    public CategoryResponse update(@NotNull @RequestParam final UpdateCategoryRequest request) {
        return adminCategoryService.updateById(request);
    }
}
