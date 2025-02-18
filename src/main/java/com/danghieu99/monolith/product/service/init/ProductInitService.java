package com.danghieu99.monolith.product.service.init;

import com.danghieu99.monolith.product.entity.*;
import com.danghieu99.monolith.product.service.dao.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ProductInitService {

    private final ProductDaoService productDaoService;
    private final ProductCategoryDaoService productCategoryDaoService;
    private final VariantDaoService variantDaoService;
    private final AttributeDaoService attributeDaoService;
    private final VariantAttributeDaoService variantAttributeDaoService;

    @Transactional
    public void init() {
        if (productDaoService.getAll().isEmpty()) {
            IntStream.range(1, 50).parallel().forEach(i -> {
                var savedProduct = productDaoService.save(Product.builder().name("Default product " + i)
                        .description("Default product description " + i)
                        .shopId(i)
                        .basePrice(BigDecimal.valueOf(i))
                        .build());

                IntStream.range(1, 5).parallel().forEach(j -> {
                    productCategoryDaoService.save(productCategoryDaoService.save(ProductCategory.builder()
                            .productId(savedProduct.getId())
                            .categoryId(j).build()));
                });

                IntStream.range(1, 5).parallel().forEach(k -> {
                    var attribute = attributeDaoService.save(Attribute.builder()
                            .productId(savedProduct.getId())
                            .type("Default type " + k)
                            .value("Default value " + k)
                            .build());

                    IntStream.range(1, 5).parallel().forEach(j -> {
                        var variant = variantDaoService.save(Variant.builder()
                                .stock(j)
                                .price(BigDecimal.valueOf(j))
                                .productId(savedProduct.getId())
                                .build());

                        variantAttributeDaoService.save(variantAttributeDaoService.save(VariantAttribute.builder()
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