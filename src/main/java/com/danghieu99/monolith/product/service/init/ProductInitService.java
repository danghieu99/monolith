package com.danghieu99.monolith.product.service.init;

import com.danghieu99.monolith.product.entity.*;
import com.danghieu99.monolith.product.service.product.daoservice.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ProductInitService {

    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final VariantService variantService;
    private final AttributeService attributeService;
    private final VariantAttributeService variantAttributeService;

    @Transactional
    public void init() {
        if (productService.getAll().isEmpty()) {
            IntStream.range(1, 50).parallel().forEach(i -> {
                var savedProduct = productService.save(Product.builder().name("Default product " + i)
                        .description("Default product description " + i)
                        .shopId(i)
                        .basePrice(BigDecimal.valueOf(i))
                        .build());

                IntStream.range(1, 5).parallel().forEach(j -> {
                    productCategoryService.save(productCategoryService.save(ProductCategory.builder()
                            .productId(savedProduct.getId())
                            .categoryId(j).build()));
                });

                IntStream.range(1, 5).parallel().forEach(k -> {
                    var attribute = attributeService.save(Attribute.builder()
                            .productId(savedProduct.getId())
                            .type("Default type " + k)
                            .value("Default value " + k)
                            .build());

                    IntStream.range(1, 5).parallel().forEach(j -> {
                        var variant = variantService.save(Variant.builder()
                                .stock(j)
                                .price(BigDecimal.valueOf(j))
                                .productId(savedProduct.getId())
                                .build());

                        variantAttributeService.save(variantAttributeService.save(VariantAttribute.builder()
                                .attributeId(attribute.getId())
                                .variantId(variant.getId())
                                .attributeType(attribute.getType())
                                .build()));
                    });
                });
            });
        }
    }
}