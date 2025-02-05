package com.danghieu99.monolith.product.controller.product;

import com.danghieu99.monolith.product.service.product.UserProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final UserProductService userProductService;

    @GetMapping("")
    public ResponseEntity<?> getAllProducts(@RequestParam(required = false) @PageableDefault(size = 25, direction = Sort.Direction.ASC, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(userProductService.getAll());
    }

//    @GetMapping("/search")
//    public ResponseEntity<?> search(@RequestParam String keyword, @RequestParam(required = false) @PageableDefault(size = 25, direction = Sort.Direction.ASC, sort = "name") Pageable pageable) {
//        return ResponseEntity.ok(userProductService.searchByNameAndCategory(keyword));
//    }
//
//    @GetMapping("/categories")
//    public ResponseEntity<?> getCategories(@RequestParam(required = false) Pageable pageable) {
//        return ResponseEntity.ok(userProductService.getAllCategories());
//    }
//
//    public ResponseEntity<?> filterSearchResults(UserFilterSearchRequest request, @RequestParam(required = false) Pageable pageable) {
//        return ResponseEntity.ok(userProductService.filterSearchRequest(request));
//    }
//
//    @GetMapping("/product/{uuid}")
//    public ResponseEntity<?> getProductDetailsByUUID(@PathVariable String uuid, @RequestParam(required = false) Pageable pageable) {
//        return ResponseEntity.ok(userProductService.getProductDetailsByUUID(uuid));
//    }
//
//    public ResponseEntity<?> foo() {
//        return ResponseEntity.ok();
//    }
//
//    public ResponseEntity<?> foo() {
//        return ResponseEntity.ok();
//    }
//
//    public ResponseEntity<?> foo() {
//        return ResponseEntity.ok();
//    }
//
//    public ResponseEntity<?> foo() {
//        return ResponseEntity.ok();
//    }
}
