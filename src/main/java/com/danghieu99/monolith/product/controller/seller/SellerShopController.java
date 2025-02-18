package com.danghieu99.monolith.product.controller.seller;

import com.danghieu99.monolith.product.dto.request.SaveShopRequest;
import com.danghieu99.monolith.product.dto.request.UpdateShopDetailsRequest;
import com.danghieu99.monolith.product.dto.response.ShopDetailsResponse;
import com.danghieu99.monolith.product.service.shop.SellerShopService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/seller/shop")
@RequiredArgsConstructor
public class SellerShopController {

    private final SellerShopService sellerShopService;

    @PostMapping("")
    public ShopDetailsResponse create(@NotNull @RequestParam SaveShopRequest request) {
        return sellerShopService.createCurrentUserShop(request);
    }

    @DeleteMapping("")
    public ResponseEntity<?> delete() {
        sellerShopService.deleteCurrentUserShop();
        return ResponseEntity.ok().build();
    }

    @PatchMapping("")
    public ShopDetailsResponse editDetails(UpdateShopDetailsRequest request) {
        return sellerShopService.editCurrentUserShopDetails(request);
    }
}