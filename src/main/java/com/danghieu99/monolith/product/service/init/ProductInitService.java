package com.danghieu99.monolith.product.service.init;

import com.danghieu99.monolith.product.entity.Category;
import com.danghieu99.monolith.product.entity.Product;
import com.danghieu99.monolith.product.entity.Shop;
import com.danghieu99.monolith.product.enums.EShopStatus;
import com.danghieu99.monolith.product.service.CategoryService;
import com.danghieu99.monolith.product.service.ProductCrudService;
import com.danghieu99.monolith.product.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductInitService {

    private final ProductCrudService productCrudService;
    private final ShopService shopService;
    private final CategoryService categoryService;

    public void init() {
        if (productCrudService.getAll().isEmpty()) {
            Set<Category> categories = new HashSet<>();
            categories.add(categoryService.getById(1));
            productCrudService.create(Product.builder()
                    .name("Default Product")
                    .description("Default product description")
                    .shop(shopService.getById(1))
                    .categories(categories)
                    .build());
        }
    }
}
