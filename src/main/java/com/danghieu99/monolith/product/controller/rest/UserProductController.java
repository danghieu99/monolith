package com.danghieu99.monolith.product.controller.rest;

import com.danghieu99.monolith.product.service.UserProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class UserProductController {

    private final UserProductService userProductService;

    public ResponseEntity<?> getAllProducts(Pageable pageable) {
        return null;
    }
}
