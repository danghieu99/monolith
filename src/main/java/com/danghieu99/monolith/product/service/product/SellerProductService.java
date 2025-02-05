package com.danghieu99.monolith.product.service.product;

import com.danghieu99.monolith.product.dto.request.SellerSaveProductRequest;
import com.danghieu99.monolith.product.entity.Product;
import com.danghieu99.monolith.product.entity.Variant;
import com.danghieu99.monolith.product.mapper.ProductMapper;
import com.danghieu99.monolith.product.service.category.CategoryCrudService;
import com.danghieu99.monolith.product.service.shop.ShopCrudService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerProductService {

    private final ProductMapper mapper;

    private final ShopCrudService shopCrudService;

    private final CategoryCrudService categoryCrudService;

    private final ProductCrudService productCrudService;

    @Transactional
    public Product addProduct(SellerSaveProductRequest request) {
        Product newProduct = mapper.toProduct(request);
        newProduct.setShop(shopCrudService.getByUUID(request.getShopUUID()));
        newProduct.setCategories(request.getCategories().stream().map(categoryCrudService::getByName).collect(Collectors.toSet()));
        newProduct.setVariants(request.getVariants().stream()
                .map(variant -> Variant.builder()
                        .attributes(variant.getAttributes())
                        .stock(variant.getStock())
                        .price(variant.getPrice())
                        .build())
                .collect(Collectors.toSet()));
        return productCrudService.create(newProduct);
    }
}
