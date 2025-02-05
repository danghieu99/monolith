package com.danghieu99.monolith.product.controller.product;

import com.danghieu99.monolith.product.dto.request.SellerSaveProductRequest;
import com.danghieu99.monolith.product.service.product.SellerProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/seller/products")
public class SellerProductController {

    private final SellerProductService sellerProductService;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody SellerSaveProductRequest request) {
        return ResponseEntity.ok(sellerProductService.addProduct(request));
    }
}
