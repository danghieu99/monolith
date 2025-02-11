package com.danghieu99.monolith.product.service.init;

import com.danghieu99.monolith.product.entity.Product;
import com.danghieu99.monolith.product.entity.ProductCategory;
import com.danghieu99.monolith.product.entity.Variant;
import com.danghieu99.monolith.product.service.product.ProductCategoryCrudService;
import com.danghieu99.monolith.product.service.product.ProductCrudService;
import com.danghieu99.monolith.product.service.product.VariantCrudService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class ProductInitService {

    private final ProductCrudService productCrudService;
    private final ProductCategoryCrudService productCategoryCrudService;
    private final VariantCrudService variantCrudService;

    @Transactional
    public void init() {
        if (productCrudService.getAll().isEmpty()) {
            IntStream.range(1, 50).parallel().forEach(i -> {
                var savedProduct = productCrudService.create(Product.builder().name("Default product " + i)
                        .description("Default product description " + i)
                        .shopId(i)
                        .basePrice(BigDecimal.valueOf(i))
                        .build());

                Set<ProductCategory> pCategories = new HashSet<>();
                IntStream.range(1, 5).parallel().forEach(j -> {
                    pCategories.add(ProductCategory.builder()
                            .productId(savedProduct.getId())
                            .categoryId(j).build());
                });
                productCategoryCrudService.saveAll(pCategories);

                Set<Variant> variants = new HashSet<>();
                IntStream.range(1, 5).parallel().forEach(j -> {
                    Map<String, String> attributes = new HashMap<>();
                    IntStream.range(1, 5).parallel().forEach(k -> {
                        attributes.put("Default type " + k, "Default value " + k);
                    });
                    variants.add(Variant.builder()
                            .attributes(attributes)
                            .stock(j)
                            .price(BigDecimal.valueOf(j))
                            .productId(savedProduct.getId())
                            .build());
                });
                variantCrudService.saveAll(variants);
            });
        }
    }
}