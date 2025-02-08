package com.danghieu99.monolith.product.controller.category;

import com.danghieu99.monolith.product.service.category.CategoryCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryCrudService categoryCrudService;

    @GetMapping("")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok().body(categoryCrudService.getAll());
    }

    @GetMapping("/get")
    public ResponseEntity<?> getCategoryByName(@RequestParam String name) {
        return ResponseEntity.ok().body(categoryCrudService.getByName(name));
    }
}
