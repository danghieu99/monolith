package com.danghieu99.monolith.product.controller.seller;

import com.danghieu99.monolith.product.dto.request.SaveProductRequest;
import com.danghieu99.monolith.product.dto.request.SaveVariantRequest;
import com.danghieu99.monolith.product.dto.request.UpdateProductDetailsRequest;
import com.danghieu99.monolith.product.dto.response.VariantDetailsResponse;
import com.danghieu99.monolith.product.dto.response.ProductDetailsResponse;
import com.danghieu99.monolith.product.service.product.SellerProductService;
import jakarta.transaction.Transactional;
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
@RequestMapping("/api/v1/seller/product")
public class SellerProductController {

    private final SellerProductService sellerProductService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCurrentShop(@RequestBody @NotNull SaveProductRequest request) {
        sellerProductService.addToCurrentShop(request);
        return ResponseEntity.ok().build();
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
    public Page<VariantDetailsResponse> getVariantsByProductUUID(@RequestParam @NotBlank String productUUID, @RequestParam @PageableDefault Pageable pageable) {
        return sellerProductService.getVariantsByProductUUID(productUUID, pageable);
    }

    @PostMapping("/variant")
    public ResponseEntity<?> addVariant(@RequestParam @NotNull SaveVariantRequest request) {
        sellerProductService.addVariant(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/variant")
    public ResponseEntity<?> updatePriceStockByUUID(@RequestParam @NotBlank String uuid, @RequestParam @NotNull SaveVariantRequest request) {
        sellerProductService.updateVariantPriceStockByUUID(uuid, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/variant")
    public ResponseEntity<?> deleteByUUID(@RequestParam @NotBlank String variantUUID) {
        sellerProductService.deleteVariant(variantUUID);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/attribute")
    public void deleteAttributeByProductUUIDTypeValue(@RequestParam @NotBlank String productUUID,
                                                      @RequestParam @NotBlank String type,
                                                      @RequestParam @NotBlank String value) {
        sellerProductService.deleteAttributeByProductUUIDTypeValue(productUUID, type, value);
    }

    @DeleteMapping("/attribute")
    public void deleteAttributeByUUID(@RequestParam @NotBlank String attributeUUID) {
        sellerProductService.deleteAttributeByUUID(attributeUUID);
    }
}
