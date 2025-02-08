package com.danghieu99.monolith.product.service.product;

import com.danghieu99.monolith.product.dto.request.SellerSaveProductRequest;
import com.danghieu99.monolith.product.dto.response.GetProductDetailsResponse;
import com.danghieu99.monolith.product.entity.Product;
import com.danghieu99.monolith.product.entity.Variant;
import com.danghieu99.monolith.product.mapper.ProductMapper;
import com.danghieu99.monolith.product.service.category.CategoryCrudService;
import com.danghieu99.monolith.product.service.shop.ShopCrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerProductService {

    private final ProductMapper productMapper;

    private final ShopCrudService shopCrudService;

    private final CategoryCrudService categoryCrudService;

    private final ProductCrudService productCrudService;

    public GetProductDetailsResponse addProduct(SellerSaveProductRequest request) {
        Product newProduct = productMapper.toProduct(request);
        newProduct.setShop(shopCrudService.getByUUID(request.getShopUUID()));
        newProduct.setCategories(request.getCategories().stream().map(categoryCrudService::getByName).collect(Collectors.toSet()));
        newProduct.setVariants(request.getVariants().stream()
                .map(attribute -> Variant.builder()
                        .attributes(attribute)
                        .build())
                .collect(Collectors.toUnmodifiableSet()));
        return productMapper.toGetProductDetailsResponse(productCrudService.create(newProduct));
    }
}
