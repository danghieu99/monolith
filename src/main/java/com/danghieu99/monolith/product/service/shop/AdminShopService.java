package com.danghieu99.monolith.product.service.shop;

import com.danghieu99.monolith.product.service.dao.ShopDaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminShopService {

    private final ShopDaoService shopDaoService;

}