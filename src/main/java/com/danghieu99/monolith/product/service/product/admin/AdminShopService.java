package com.danghieu99.monolith.product.service.product.admin;

import com.danghieu99.monolith.product.service.product.daoservice.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminShopService {

    private final ShopService shopService;

}