package com.danghieu99.monolith.cart.controller;

import com.danghieu99.monolith.cart.dto.CartItemDto;
import com.danghieu99.monolith.cart.service.cart.UserCartItemService;
import com.danghieu99.monolith.cart.service.savedcart.SavedCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user/cart")
public class UserCartController {

    private final UserCartItemService userCartItemService;

    private final SavedCartService savedCartService;

    @GetMapping("")
    public ResponseEntity<?> getCurrentUserCart() {
        return ResponseEntity.ok().body(userCartItemService.getCurrentUserCartItems());
    }

    @PostMapping("")
    public ResponseEntity<?> addCartItem(@RequestBody CartItemDto cartItemDto) {
        return ResponseEntity.ok().body(userCartItemService.addCartItem(cartItemDto));
    }

    @PutMapping("")
    public ResponseEntity<?> updateCartItem(@RequestBody CartItemDto cartItemDto) {
        return ResponseEntity.ok().body(userCartItemService.updateCartItem(cartItemDto));
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteCartItem(@RequestParam String id) {
        return ResponseEntity.ok().body(userCartItemService.deleteCartItem(id));
    }
}
