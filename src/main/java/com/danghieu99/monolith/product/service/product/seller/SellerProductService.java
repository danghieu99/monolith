package com.danghieu99.monolith.product.service.product.seller;

import com.danghieu99.monolith.product.dto.request.SaveProductRequest;
import com.danghieu99.monolith.product.dto.request.SaveVariantRequest;
import com.danghieu99.monolith.product.dto.request.UpdateProductDetailsRequest;
import com.danghieu99.monolith.product.dto.response.VariantDetailsResponse;
import com.danghieu99.monolith.product.dto.response.ProductDetailsResponse;
import com.danghieu99.monolith.product.entity.*;
import com.danghieu99.monolith.product.mapper.ProductMapper;
import com.danghieu99.monolith.product.mapper.VariantMapper;
import com.danghieu99.monolith.product.service.product.daoservice.*;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerProductService {

    private final ProductCategoryService productCategoryService;
    private final ProductMapper productMapper;
    private final ShopService shopService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final VariantService variantService;
    private final AuthenticationService authenticationService;
    private final VariantMapper variantMapper;
    private final AttributeService attributeService;
    private final VariantAttributeService variantAttributeService;

    public Page<ProductDetailsResponse> getAllByCurrentShop(@NotNull Pageable pageable) {
        Page<Product> products = productService.getByShopId(authenticationService.getCurrentUserDetails().getId(), pageable);
        return products.map(productMapper::toGetProductDetailsResponse);
    }

    @Transactional
    public ProductDetailsResponse addToCurrentShop(SaveProductRequest request) {
        Set<Variant> variants = new HashSet<>();
        Set<VariantAttribute> variantAttributes = new HashSet<>();
        Set<ProductCategory> pCategories = new HashSet<>();
        Set<VariantDetailsResponse> variantsResponse = new HashSet<>();

        Product newProduct = productMapper.toProduct(request);
        int userId = authenticationService.getCurrentUserDetails().getId();
        newProduct.setShopId(shopService.getById(userId).getId());
        var savedProduct = productService.save(newProduct);


        request.getCategories()
                .forEach(category -> pCategories.add(ProductCategory.builder()
                        .categoryId(categoryService.getByName(category.trim()).getId())
                        .productId(savedProduct.getId())
                        .build()));

        request.getVariants().forEach(requestVariant -> {
            var variant = variantMapper.toVariant(requestVariant);
            variant.setProductId(savedProduct.getId());
            variant.setPrice(requestVariant.getPrice());
            variant.setStock(requestVariant.getStock());
            var savedVariant = variantService.save(variant);
            variantsResponse.add(variantMapper.toResponse(savedVariant));

            requestVariant.getAttributes().forEach((key, value) -> {
                var savedAttribute = attributeService.save(Attribute.builder()
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
        productCategoryService.saveAll(pCategories);
        variantService.saveAll(variants);
        variantAttributeService.saveAll(variantAttributes);

        var response = productMapper.toGetProductDetailsResponse(savedProduct);
        response.setVariantUuids(variants.stream().map(variant -> variant.getId().toString()).collect(Collectors.toSet()));
        return response;
    }

    @Transactional
    public void updateProductDetailsByUUID(String uuid, UpdateProductDetailsRequest request) {
        var product = productService.getByUUID(UUID.fromString(uuid));
        if (request.getName() != null && !request.getName().isBlank()) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            product.setDescription(request.getDescription());
        }
        productService.save(product);
    }

    @Transactional
    public void deleteProductByUUID(String uuid) {
        productService.deleteByUUID(UUID.fromString(uuid));
    }

    @Transactional
    public Page<VariantDetailsResponse> getVariantsByProductUUID(@NotBlank String productUUID, @NotNull Pageable pageable) {
        return variantService.getByProductUUID(UUID.fromString(productUUID), pageable).map(variantMapper::toResponse);
    }

    @Transactional
    public VariantDetailsResponse addVariant(SaveVariantRequest request) {
        return variantMapper.toResponse(variantService.save(variantMapper.toVariant(request)));
    }

    @Transactional
    public VariantDetailsResponse updateVariantPriceStock(String uuid, SaveVariantRequest request) {
        return variantMapper.toResponse(variantService.updateByUUID(UUID.fromString(uuid), variantMapper.toVariant(request)));
    }

    @Transactional
    public void deleteVariant(String variantUUID) {
        variantService.deleteByUUID(UUID.fromString(variantUUID));
    }
}
