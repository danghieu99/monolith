package com.danghieu99.monolith.product.service.product;

import com.danghieu99.monolith.product.dto.request.SaveProductRequest;
import com.danghieu99.monolith.product.dto.request.SaveVariantRequest;
import com.danghieu99.monolith.product.dto.request.UpdateProductDetailsRequest;
import com.danghieu99.monolith.product.dto.response.VariantDetailsResponse;
import com.danghieu99.monolith.product.dto.response.ProductDetailsResponse;
import com.danghieu99.monolith.product.entity.*;
import com.danghieu99.monolith.product.entity.join.ProductCategory;
import com.danghieu99.monolith.product.entity.join.ProductShop;
import com.danghieu99.monolith.product.entity.join.VariantAttribute;
import com.danghieu99.monolith.product.mapper.ProductMapper;
import com.danghieu99.monolith.product.mapper.VariantMapper;
import com.danghieu99.monolith.product.repository.jpa.join.ProductCategoryRepository;
import com.danghieu99.monolith.product.repository.jpa.join.ProductShopRepository;
import com.danghieu99.monolith.product.repository.jpa.join.VariantAttributeRepository;
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

    private final ProductMapper productMapper;
    private final ShopDaoService shopDaoService;
    private final CategoryDaoService categoryDaoService;
    private final ProductDaoService productDaoService;
    private final VariantDaoService variantDaoService;
    private final AuthenticationService authenticationService;
    private final VariantMapper variantMapper;
    private final AttributeDaoService attributeDaoService;
    private final ProductCategoryRepository pCategoryRepository;
    private final VariantAttributeRepository vAttributeRepository;
    private final ProductShopRepository productShopRepository;
    private final VariantAttributeRepository variantAttributeRepository;

    public Page<ProductDetailsResponse> getAllByCurrentShop(@NotNull Pageable pageable) {
        Page<Product> products = productDaoService.getByShopId(authenticationService.getCurrentUserDetails().getId(), pageable);
        return products.map(productMapper::toGetProductDetailsResponse);
    }

    @Transactional
    public void addToCurrentShop(@NotNull SaveProductRequest request) {
        ProductShop productShop;
        Set<ProductCategory> productCategories = new HashSet<>();
        Set<Variant> variants = new HashSet<>();
        Set<VariantAttribute> variantAttributes = new HashSet<>();

        Product newProduct = productMapper.toProduct(request);
        int userId = authenticationService.getCurrentUserDetails().getId();
        var savedProduct = productDaoService.save(newProduct);
        productShop = ProductShop.builder()
                .productId(savedProduct.getId())
                .shopId(shopDaoService.getByAccountId(userId).getId())
                .build();
        request.getCategories()
                .forEach(category -> productCategories.add(ProductCategory.builder()
                        .categoryId(categoryDaoService.getByName(category.trim()).getId())
                        .productId(savedProduct.getId())
                        .build()));
        request.getVariants().forEach(requestVariant -> {
            var variant = variantMapper.toVariant(requestVariant);
            variant.setProductId(savedProduct.getId());
            variant.setPrice(requestVariant.getPrice());
            variant.setStock(requestVariant.getStock());
            var savedVariant = variantDaoService.save(variant);
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
        productShopRepository.save(productShop);
        pCategoryRepository.saveAll(productCategories);
        variantDaoService.saveAll(variants);
        vAttributeRepository.saveAll(variantAttributes);
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
        productDaoService.deleteByUUIDCascading(UUID.fromString(uuid));
    }

    @Transactional
    public Page<VariantDetailsResponse> getVariantsByProductUUID(@NotBlank String productUUID, @NotNull Pageable pageable) {
        return variantDaoService.getByProductUUID(UUID.fromString(productUUID), pageable).map(variantMapper::toResponse);
    }

    @Transactional
    public void addVariant(@NotNull SaveVariantRequest request) {
        variantDaoService.save(variantMapper.toVariant(request));
    }

    @Transactional
    public void updateVariantPriceStockByUUID(@NotBlank String variantUUID, @NotNull SaveVariantRequest request) {
        variantDaoService.updateByUUID(UUID.fromString(variantUUID), variantMapper.toVariant(request));
    }

    @Transactional
    public void deleteVariant(@NotBlank String variantUUID) {
        variantDaoService.deleteByUUIDCascading(UUID.fromString(variantUUID));
    }
}