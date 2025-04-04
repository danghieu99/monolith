package com.danghieu99.monolith.order.controller;

import com.danghieu99.monolith.order.service.SellerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/seller/order")
@RequiredArgsConstructor
@Validated
public class SellerOrderController {

    private final SellerOrderService sellerOrderService;


}