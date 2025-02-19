package com.danghieu99.monolith.product.service.init;

import com.danghieu99.monolith.product.entity.*;
import com.danghieu99.monolith.product.entity.join.ProductCategory;
import com.danghieu99.monolith.product.entity.join.ProductShop;
import com.danghieu99.monolith.product.entity.join.VariantAttribute;
import com.danghieu99.monolith.product.repository.jpa.join.ProductCategoryRepository;
import com.danghieu99.monolith.product.repository.jpa.join.ProductShopRepository;
import com.danghieu99.monolith.product.repository.jpa.join.VariantAttributeRepository;
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
    private final VariantDaoService variantDaoService;
    private final ProductShopRepository pShopRepository;
    private final AttributeDaoService attributeDaoService;
    private final ProductCategoryRepository pCategoryRepository;
    private final VariantAttributeRepository vAttributeRepository;

    @Transactional
    public void init() {
        if (productDaoService.getAll().isEmpty()) {
            IntStream.range(1, 50).parallel().forEach(i -> {
                var savedProduct = productDaoService.save(Product.builder().name("Default product " + i)
                        .description("Default product description " + i)
                        .basePrice(BigDecimal.valueOf(i))
                        .build());
                pShopRepository.save(ProductShop.builder()
                        .productId(savedProduct.getId())
                        .shopId(i)
                        .build());

                IntStream.range(1, 5).parallel().forEach(j -> {
                    pCategoryRepository.save(pCategoryRepository.save(ProductCategory.builder()
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

                        vAttributeRepository.save(vAttributeRepository.save(VariantAttribute.builder()
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