package com.danghieu99.monolith.product.service.init;

import com.danghieu99.monolith.product.entity.Shop;
import com.danghieu99.monolith.product.enums.EShopStatus;
import com.danghieu99.monolith.product.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopInitService {

    private final ShopService shopService;

    public void init() {
        if (shopService.getAll().isEmpty()) {
            shopService.create(Shop.builder()
                    .name("Default shop")
                    .description("Default shop description")
                    .status(EShopStatus.ACTIVE)
                    .build());
        }
    }
}
