package com.danghieu99.monolith.product.service.shop;

import com.danghieu99.monolith.product.dto.request.SaveShopRequest;
import com.danghieu99.monolith.product.dto.request.UpdateShopDetailsRequest;
import com.danghieu99.monolith.product.dto.response.ShopDetailsResponse;
import com.danghieu99.monolith.product.mapper.ShopMapper;
import com.danghieu99.monolith.product.service.dao.ShopDaoService;
import com.danghieu99.monolith.security.service.auth.AuthenticationService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerShopService {

    private final ShopMapper shopMapper;
    private final ShopDaoService shopDaoService;
    private final AuthenticationService authenticationService;

    @Transactional
    public ShopDetailsResponse createCurrentUserShop(@NotNull SaveShopRequest request) {
        var newShop = shopMapper.toShop(request);
        newShop.setAccountId(authenticationService.getCurrentUserDetails().getId());
        return shopMapper.toResponse(shopDaoService.save(newShop));
    }

    @Transactional
    public void deleteCurrentUserShop() {
        shopDaoService.deleteById(authenticationService.getCurrentUserDetails().getId());
    }

    @Transactional
    public ShopDetailsResponse editCurrentUserShopDetails(@NotNull UpdateShopDetailsRequest request) {
        int shopId = authenticationService.getCurrentUserDetails().getId();
        var shop = shopDaoService.getById(shopId);
        if (request.getName() != null && !request.getName().isEmpty()) {
            shop.setName(request.getName());
        }
        if (request.getDescription() != null && !request.getDescription().isEmpty()) {
            shop.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            shop.setStatus(request.getStatus());
        }
        var updatedShop = shopDaoService.updateById(shop.getId(), shop);
        return shopMapper.toResponse(updatedShop);
    }
}