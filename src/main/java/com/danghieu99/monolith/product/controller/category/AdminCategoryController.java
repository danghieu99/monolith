package com.danghieu99.monolith.product.controller.category;

import com.danghieu99.monolith.product.entity.Category;
import com.danghieu99.monolith.product.service.category.AdminCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        return ResponseEntity.ok().body(adminCategoryService.save(category));
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        return ResponseEntity.ok().body(adminCategoryService.update(category));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCategory(@RequestParam int id) {
        adminCategoryService.deleteById(id);
        return ResponseEntity.ok("Delete category with id " + id + " success");
    }
}
