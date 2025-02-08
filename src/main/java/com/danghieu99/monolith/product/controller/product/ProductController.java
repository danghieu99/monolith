package com.danghieu99.monolith.product.controller.product;

import com.danghieu99.monolith.product.dto.request.SearchByParamsRequest;
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

    @GetMapping("/search")
    public ResponseEntity<?> advancedSearchRequest(SearchByParamsRequest request, @RequestParam(required = false) Pageable pageable) {
        return ResponseEntity.ok(userProductService.searchByParams(request, pageable));
    }

    @GetMapping("/details/{uuid}")
    public ResponseEntity<?> getProductDetailsByUUID(@PathVariable String uuid) {
        return ResponseEntity.ok(userProductService.getProductDetailsByUUID(uuid));
    }
}
