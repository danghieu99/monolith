package com.danghieu99.monolith.product.service.init;

import com.danghieu99.monolith.product.entity.Shop;
import com.danghieu99.monolith.product.constant.EShopStatus;
import com.danghieu99.monolith.product.service.shop.ShopCrudService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ShopInitService {

    private final ShopCrudService shopCrudService;

    @Transactional
    public void init() {
        if (shopCrudService.getAll().isEmpty()) {
            IntStream.range(1, 50).parallel().forEach(i -> {
                shopCrudService.create(Shop.builder()
                        .name("Default shop " + i)
                        .description("Default shop description " + i)
                        .status(i % 5 != 0 ? EShopStatus.SHOP_ACTIVE : EShopStatus.SHOP_INACTIVE)
                        .build());
            });
        }
    }
}
