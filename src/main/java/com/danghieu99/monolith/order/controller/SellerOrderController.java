package com.danghieu99.monolith.order.controller;

import com.danghieu99.monolith.order.constant.EOrderStatus;
import com.danghieu99.monolith.order.service.SellerOrderService;
import com.danghieu99.monolith.security.config.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/seller/order")
@RequiredArgsConstructor
@Validated
public class SellerOrderController {

    private final SellerOrderService sellerOrderService;

    @GetMapping("")
    public ResponseEntity<?> getByCurrentShopAccount(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                     Pageable pageable) {
        return ResponseEntity.ok(sellerOrderService.getByCurrentShopAccount(userDetails, pageable));
    }

    public ResponseEntity<?> updateStatusByUUID(String orderUUID,
                                                EOrderStatus status) {
        return null;
    }
}