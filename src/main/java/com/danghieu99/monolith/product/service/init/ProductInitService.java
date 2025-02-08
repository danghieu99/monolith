package com.danghieu99.monolith.product.service.init;

import com.danghieu99.monolith.product.entity.Category;
import com.danghieu99.monolith.product.entity.Product;
import com.danghieu99.monolith.product.entity.Variant;
import com.danghieu99.monolith.product.service.category.CategoryCrudService;
import com.danghieu99.monolith.product.service.product.ProductCrudService;
import com.danghieu99.monolith.product.service.shop.ShopCrudService;
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
    private final ShopCrudService shopCrudService;
    private final CategoryCrudService categoryCrudService;

    @Transactional
    public void init() {
        if (productCrudService.getAll().isEmpty()) {
            IntStream.range(1, 50).parallel().forEach(i -> {
                Set<Category> categories = new HashSet<>();
                categories.add(categoryCrudService.getById(i));
                Product newProduct = new Product("Default product " + i,
                        "Default product description " + i,
                        categories,
                        shopCrudService.getById(i));

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
                            .product(newProduct)
                            .build());
                });

                newProduct.setVariants(variants);
                productCrudService.create(newProduct);
            });
        }
    }
}