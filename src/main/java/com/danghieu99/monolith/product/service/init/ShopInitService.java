package com.danghieu99.monolith.product.service.init;

import com.danghieu99.monolith.product.entity.Shop;
import com.danghieu99.monolith.product.constant.EShopStatus;
import com.danghieu99.monolith.product.service.dao.ShopDaoService;
import com.danghieu99.monolith.security.constant.ERole;
import com.danghieu99.monolith.security.service.dao.AccountDaoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShopInitService {

    private final ShopDaoService shopDaoService;
    private final AccountDaoService accountDaoService;

    @Transactional
    public void init() {
        if (shopDaoService.getAll().isEmpty()) {
            var sellers = accountDaoService.getByRole(ERole.ROLE_SELLER);
            Set<Shop> shops = new HashSet<>();
            sellers.forEach(seller -> {
                shops.add(Shop.builder()
                        .name("Default shop " + seller.getPhone())
                        .description("Default shop description " + seller.getPhone())
                        .accountId(seller.getId())
                        .status(seller.getId() % 5 != 0 ? EShopStatus.SHOP_ACTIVE : EShopStatus.SHOP_INACTIVE)
                        .build());
            });
            shopDaoService.saveAll(shops);
        }
    }
}
