package com.danghieu99.monolith.product.service.product.user;

import com.danghieu99.monolith.product.dto.response.ShopDetailsResponse;
import com.danghieu99.monolith.product.mapper.ShopMapper;
import com.danghieu99.monolith.product.service.product.daoservice.ShopService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserShopService {

    private final ShopService shopService;
    private final ShopMapper shopMapper;

    public ShopDetailsResponse getByUUID(@NotBlank String uuid) {
        return shopMapper.toResponse(shopService.getByUUID(UUID.fromString(uuid)));
    }

    public ShopDetailsResponse getByName(@NotBlank String name) {
        return shopMapper.toResponse(shopService.getByName(name));
    }

    public Page<ShopDetailsResponse> getByNameContaining(@NotBlank String name, @NotNull Pageable pageable) {
        return shopService.getByNameContaining(name, pageable).map(shopMapper::toResponse);
    }
}
