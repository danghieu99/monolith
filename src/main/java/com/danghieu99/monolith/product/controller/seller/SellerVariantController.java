package com.danghieu99.monolith.product.controller.seller;

import com.danghieu99.monolith.product.dto.request.SaveVariantRequest;
import com.danghieu99.monolith.product.dto.response.VariantDetailsResponse;
import com.danghieu99.monolith.product.service.product.SellerProductService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/seller/variant")
public class SellerVariantController {

    private final SellerProductService sellerProductService;

    @GetMapping("")
    public Page<VariantDetailsResponse> getVariantsByProductUUID(@RequestParam @NotBlank String productUUID, @RequestParam @PageableDefault Pageable pageable) {
        return sellerProductService.getVariantsByProductUUID(productUUID, pageable);
    }

    @PostMapping("")
    public ResponseEntity<?> addVariant(@RequestParam @NotNull SaveVariantRequest request) {
        sellerProductService.addVariant(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("")
    public ResponseEntity<?> updateVariantPriceStockByUUID(@RequestParam @NotBlank String uuid, @RequestParam @NotNull SaveVariantRequest request) {
        sellerProductService.updateVariantPriceStockByUUID(uuid, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteVariantByUUID(@RequestParam @NotBlank String variantUUID) {
        sellerProductService.deleteVariant(variantUUID);
        return ResponseEntity.ok().build();
    }
}
