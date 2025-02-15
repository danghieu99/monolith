package com.danghieu99.monolith.product.controller.seller;

import com.danghieu99.monolith.product.dto.request.SaveProductRequest;
import com.danghieu99.monolith.product.dto.request.SaveVariantRequest;
import com.danghieu99.monolith.product.dto.request.UpdateProductDetailsRequest;
import com.danghieu99.monolith.product.dto.response.VariantDetailsResponse;
import com.danghieu99.monolith.product.dto.response.ProductDetailsResponse;
import com.danghieu99.monolith.product.service.product.seller.SellerProductService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/seller/product")
public class SellerProductController {

    private final SellerProductService sellerProductService;

    @PostMapping("/add")
    public ProductDetailsResponse addToCurrentShop(@RequestBody @NotNull SaveProductRequest request) {
        return sellerProductService.addToCurrentShop(request);
    }

    @GetMapping("")
    public Page<ProductDetailsResponse> getAllByCurrentShop(@PageableDefault Pageable pageable) {
        return sellerProductService.getAllByCurrentShop(pageable);
    }

    @PatchMapping("")
    public ResponseEntity<?> updateProductDetailsByUUID(@RequestParam @NotBlank String uuid, @RequestBody UpdateProductDetailsRequest request) {
        sellerProductService.updateProductDetailsByUUID(uuid, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteProductByUUID(@RequestParam @NotBlank String uuid) {
        sellerProductService.deleteProductByUUID(uuid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/variant")
    public Page<VariantDetailsResponse> getVariantsByProductUUID(@RequestParam @NotBlank String productUUID, @NotNull Pageable pageable) {
        return sellerProductService.getVariantsByProductUUID(productUUID, pageable);
    }

    @PostMapping("/variant")
    public VariantDetailsResponse addVariant(@RequestParam @NotBlank SaveVariantRequest request) {
        return sellerProductService.addVariant(request);
    }

    @PatchMapping("/variant")
    public VariantDetailsResponse updateVariantByUUID(@RequestParam @NotBlank String uuid, @RequestParam @NotBlank SaveVariantRequest request) {
        return sellerProductService.updateVariantPriceStock(uuid, request);
    }

    @DeleteMapping("/variant")
    public ResponseEntity<?> deleteVariant(@RequestParam @NotBlank UUID variantUUID) {
        sellerProductService.deleteVariant(variantUUID);
        return ResponseEntity.ok().build();
    }
}
