package com.danghieu99.monolith.product.service.product;

import com.danghieu99.monolith.product.dto.request.SaveProductRequest;
import com.danghieu99.monolith.product.dto.request.SaveVariantRequest;
import com.danghieu99.monolith.product.dto.request.UpdateProductDetailsRequest;
import com.danghieu99.monolith.product.dto.response.VariantDetailsResponse;
import com.danghieu99.monolith.product.dto.response.ProductDetailsResponse;
import com.danghieu99.monolith.product.entity.*;
import com.danghieu99.monolith.product.mapper.ProductMapper;
import com.danghieu99.monolith.product.mapper.VariantMapper;
import com.danghieu99.monolith.product.service.dao.*;
import com.danghieu99.monolith.security.service.auth.AuthenticationService;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerProductService {

    private final ProductCategoryDaoService productCategoryDaoService;
    private final ProductMapper productMapper;
    private final ShopDaoService shopDaoService;
    private final CategoryDaoService categoryDaoService;
    private final ProductDaoService productDaoService;
    private final VariantDaoService variantDaoService;
    private final AuthenticationService authenticationService;
    private final VariantMapper variantMapper;
    private final AttributeDaoService attributeDaoService;
    private final VariantAttributeDaoService variantAttributeDaoService;

    public Page<ProductDetailsResponse> getAllByCurrentShop(@NotNull Pageable pageable) {
        Page<Product> products = productDaoService.getByShopId(authenticationService.getCurrentUserDetails().getId(), pageable);
        return products.map(productMapper::toGetProductDetailsResponse);
    }

    @Transactional
    public ProductDetailsResponse addToCurrentShop(@NotNull SaveProductRequest request) {
        Set<Variant> variants = new HashSet<>();
        Set<VariantAttribute> variantAttributes = new HashSet<>();
        Set<ProductCategory> pCategories = new HashSet<>();
        Set<VariantDetailsResponse> responseVariants = new HashSet<>();

        Product newProduct = productMapper.toProduct(request);
        int userId = authenticationService.getCurrentUserDetails().getId();
        newProduct.setShopId(shopDaoService.getById(userId).getId());
        var savedProduct = productDaoService.save(newProduct);


        request.getCategories()
                .forEach(category -> pCategories.add(ProductCategory.builder()
                        .categoryId(categoryDaoService.getByName(category.trim()).getId())
                        .productId(savedProduct.getId())
                        .build()));

        request.getVariants().forEach(requestVariant -> {
            var variant = variantMapper.toVariant(requestVariant);
            variant.setProductId(savedProduct.getId());
            variant.setPrice(requestVariant.getPrice());
            variant.setStock(requestVariant.getStock());
            var savedVariant = variantDaoService.save(variant);
            responseVariants.add(variantMapper.toResponse(savedVariant));

            requestVariant.getAttributes().forEach((key, value) -> {
                var savedAttribute = attributeDaoService.save(Attribute.builder()
                        .type(key)
                        .value(value)
                        .build());
                variantAttributes.add(VariantAttribute.builder()
                        .variantId(savedVariant.getId())
                        .attributeId(savedAttribute.getId())
                        .attributeType(savedAttribute.getType())
                        .build());
            });
        });
        productCategoryDaoService.saveAll(pCategories);
        variantDaoService.saveAll(variants);
        variantAttributeDaoService.saveAll(variantAttributes);

        var response = productMapper.toGetProductDetailsResponse(savedProduct);
        response.setVariants(responseVariants);
        return response;
    }

    @Transactional
    public void updateProductDetailsByUUID(@NotBlank String uuid, @NotNull UpdateProductDetailsRequest request) {
        var product = productDaoService.getByUUID(UUID.fromString(uuid));
        if (request.getName() != null && !request.getName().isBlank()) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            product.setDescription(request.getDescription());
        }
        productDaoService.save(product);
    }

    @Transactional
    public void deleteProductByUUID(@NotBlank String uuid) {
        productDaoService.deleteByUUID(UUID.fromString(uuid));
    }

    @Transactional
    public Page<VariantDetailsResponse> getVariantsByProductUUID(@NotBlank String productUUID, @NotNull Pageable pageable) {
        return variantDaoService.getByProductUUID(UUID.fromString(productUUID), pageable).map(variantMapper::toResponse);
    }

    @Transactional
    public VariantDetailsResponse addVariant(@NotNull SaveVariantRequest request) {
        return variantMapper.toResponse(variantDaoService.save(variantMapper.toVariant(request)));
    }

    @Transactional
    public VariantDetailsResponse updateVariantPriceStockByUUID(@NotBlank String variantUUID, @NotNull SaveVariantRequest request) {
        return variantMapper.toResponse(variantDaoService.updateByUUID(UUID.fromString(variantUUID), variantMapper.toVariant(request)));
    }

    @Transactional
    public void deleteVariant(@NotBlank String variantUUID) {

        variantDaoService.deleteByUUID(UUID.fromString(variantUUID));
    }
}