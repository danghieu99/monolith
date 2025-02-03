package com.danghieu99.monolith.cart.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user/cart")
public class UserCartController {

    public ResponseEntity<?> getCurrentUserCart() {
        return ResponseEntity.ok().build();
    }
}
