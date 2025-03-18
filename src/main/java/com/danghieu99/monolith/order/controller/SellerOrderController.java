package com.danghieu99.monolith.order.controller;

import com.danghieu99.monolith.order.service.SellerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/seller/order")
@RequiredArgsConstructor
public class SellerOrderController {

    private final SellerOrderService sellerOrderService;

    @PostMapping("/cancel")
    public void cancelOderByUUID(@RequestParam String uuid) {
        sellerOrderService.cancelOderByUUID(uuid);
    }
}