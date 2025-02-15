package com.danghieu99.monolith.product.controller.admin;

import com.danghieu99.monolith.product.service.product.admin.AdminShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/shop")
@RequiredArgsConstructor
public class AdminShopController {

    private final AdminShopService adminShopService;

}
