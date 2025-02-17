package com.danghieu99.monolith.product.controller;

import com.danghieu99.monolith.product.dto.response.CategoryResponse;
import com.danghieu99.monolith.product.service.product.user.UserCategoryService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final UserCategoryService userCategoryService;

    @GetMapping("")
    public Page<CategoryResponse> getAllCategories(@PageableDefault Pageable pageable) {
        return userCategoryService.getAll(pageable);
    }

    @GetMapping("/uuid")
    public CategoryResponse getCategoryByUUID(@RequestParam @NotNull String uuid) {
        return userCategoryService.getByUUID(uuid);
    }

    @GetMapping("/sup-uuid")
    public Page<CategoryResponse> getCategoryBySuperCategoryUUID(@RequestParam @NotNull String uuid, @RequestParam @PageableDefault Pageable pageable) {
        return userCategoryService.getBySuperCategoryUUID(uuid, pageable);
    }

    @GetMapping("/sub-uuid")
    public Page<CategoryResponse> getBySubCategoryUUID(@RequestParam @NotNull String uuid, @RequestParam @PageableDefault Pageable pageable) {
        return userCategoryService.getBySubCategoryUUID(uuid, pageable);
    }

    @GetMapping("/search")
    public Page<CategoryResponse> searchByName(@RequestParam @NotNull String name, @RequestParam @PageableDefault Pageable pageable) {
        return userCategoryService.getByNameContaining(name, pageable);
    }
}
