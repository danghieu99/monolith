package com.danghieu99.monolith.product.service.shop;

import com.danghieu99.monolith.product.dto.response.ShopDetailsResponse;
import com.danghieu99.monolith.product.mapper.ShopMapper;
import com.danghieu99.monolith.product.service.dao.ShopDaoService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopDaoService shopDaoService;
    private final ShopMapper shopMapper;

    public ShopDetailsResponse getByUUID(@NotBlank String uuid) {
        return shopMapper.toResponse(shopDaoService.getByUUID(UUID.fromString(uuid)));
    }

    public ShopDetailsResponse getByName(@NotBlank String name) {
        return shopMapper.toResponse(shopDaoService.getByName(name));
    }

    public Page<ShopDetailsResponse> getByNameContaining(@NotBlank String name, @NotNull Pageable pageable) {
        return shopDaoService.getByNameContaining(name, pageable).map(shopMapper::toResponse);
    }
}
