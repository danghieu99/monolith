package com.danghieu99.monolith.cart.controller;

import com.danghieu99.monolith.cart.dto.CartItemDto;
import com.danghieu99.monolith.cart.service.SaveCartService;
import com.danghieu99.monolith.cart.service.UserCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user/cart")
public class UserCartController {

    private final UserCartService userCartService;

    private final SaveCartService saveCartService;

    @GetMapping("")
    public ResponseEntity<?> getCurrentUserCart() {
        return ResponseEntity.ok().body(userCartService.getCurrentUserCart());
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCurrentUserCart() {
        userCartService.clearCurrentUserCart();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCartItem(@RequestBody CartItemDto cartItemDto) {
        return ResponseEntity.ok().body(userCartService.addCartItem(cartItemDto));
    }

    @PutMapping("/edit")
    public ResponseEntity<?> updateCartItem(@RequestBody CartItemDto cartItemDto) {
        return ResponseEntity.ok().body(userCartService.updateCartItem(cartItemDto));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> deleteCartItem(@RequestParam String id) {
        return ResponseEntity.ok().body(userCartService.deleteCartItem(id));
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveCurrentCart() {
        userCartService.saveCurrentUserCartItems();
        return ResponseEntity.ok().build();
    }
}