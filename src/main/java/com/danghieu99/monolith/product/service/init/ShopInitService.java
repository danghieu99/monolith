package com.danghieu99.monolith.product.service.init;

import com.danghieu99.monolith.product.entity.Shop;
import com.danghieu99.monolith.product.constant.EShopStatus;
import com.danghieu99.monolith.product.service.product.daoservice.ShopService;
import com.danghieu99.monolith.security.constant.ERole;
import com.danghieu99.monolith.security.service.dao.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ShopInitService {

    private final ShopService shopService;
    private final AccountService accountService;

    @Transactional
    public void init() {
        if (shopService.getAll().isEmpty()) {
            var sellers = accountService.getByRole(ERole.ROLE_SELLER);
            Set<Shop> shops = new HashSet<>();
            sellers.forEach(seller -> {
                shops.add(Shop.builder()
                        .name("Default shop " + seller.getPhone())
                        .description("Default shop description " + seller.getPhone())
                        .accountId(seller.getId())
                        .status(seller.getId() % 5 != 0 ? EShopStatus.SHOP_ACTIVE : EShopStatus.SHOP_INACTIVE)
                        .build());
            });
            shopService.saveAll(shops);
        }
    }
}
