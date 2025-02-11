package com.danghieu99.monolith.product.service.product;

import com.danghieu99.monolith.product.dto.request.SellerSaveProductRequest;
import com.danghieu99.monolith.product.dto.response.GetProductDetailsResponse;
import com.danghieu99.monolith.product.entity.Product;
import com.danghieu99.monolith.product.entity.ProductCategory;
import com.danghieu99.monolith.product.mapper.ProductMapper;
import com.danghieu99.monolith.product.repository.jpa.ProductCategoryRepository;
import com.danghieu99.monolith.product.service.category.CategoryCrudService;
import com.danghieu99.monolith.product.service.shop.ShopCrudService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerProductService {
    private final ProductCategoryRepository productCategoryRepository;

    private final ProductMapper productMapper;
    private final ShopCrudService shopCrudService;
    private final CategoryCrudService categoryCrudService;
    private final ProductCrudService productCrudService;
    private final VariantCrudService variantCrudService;

    @Transactional
    public GetProductDetailsResponse addProduct(SellerSaveProductRequest request) {
        Product newProduct = productMapper.toProduct(request);
        newProduct.setShopId(shopCrudService.getByUUID(request.getShopUUID()).getId());
        var savedProduct = productCrudService.create(newProduct);
        var response = productMapper.toGetProductDetailsResponse(savedProduct);

        Set<ProductCategory> pCategories = new HashSet<>();
        request.getCategories()
                .forEach(category -> pCategories.add(ProductCategory.builder()
                        .categoryId(categoryCrudService.getByName(category.trim()).getId())
                        .productId(savedProduct.getId())
                        .build()));
        productCategoryRepository.saveAll(pCategories);
        response.setCategories(pCategories.stream()
                .map(pc -> categoryCrudService.getById(pc.getCategoryId())).collect(Collectors.toSet()));

        var variants = request.getVariants().stream()
                .map(v -> {
                    var variant = productMapper.toVariant(v);
                    variant.setProductId(savedProduct.getId());
                    variant.setPrice(v.getPrice());
                    variant.setStock(v.getStock());
                    return variant;
                })
                .collect(Collectors.toSet());
        variantCrudService.saveAll(variants);
        response.setVariants(variants);

        return response;
    }
}
