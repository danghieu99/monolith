package com.danghieu99.monolith.product.controller.category;

import com.danghieu99.monolith.product.entity.Category;
import com.danghieu99.monolith.product.service.category.AdminCategoryService;
import com.danghieu99.monolith.product.service.category.CategoryCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryCrudService categoryCrudService;

    private final AdminCategoryService adminCategoryService;

    @GetMapping("/category/get")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok().body(categoryCrudService.getAll());
    }

    @GetMapping("/admin/category/get")
    public ResponseEntity<?> getCategoryByName(@RequestParam String name) {
        return ResponseEntity.ok().body(categoryCrudService.getByName(name));
    }

    @PostMapping("/admin/category/add")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        return ResponseEntity.ok().body(adminCategoryService.save(category));
    }

    @PatchMapping("/admin/category/update")
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        return ResponseEntity.ok().body(adminCategoryService.update(category));
    }

    @DeleteMapping("/admin/category/delete")
    public ResponseEntity<?> deleteCategory(@RequestParam int id) {
        adminCategoryService.deleteById(id);
        return ResponseEntity.ok("Delete category with id " + id + " success");
    }
}
