package com.danghieu99.monolith.product.service.product.daoservice;

import com.danghieu99.monolith.product.entity.ProductCategory;
import com.danghieu99.monolith.product.repository.jpa.ProductCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository repository;

    @Transactional
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }

    @Transactional
    public Collection<ProductCategory> saveAll(Collection<ProductCategory> categories) {
        return repository.saveAll(categories);
    }

    public Collection<ProductCategory> getByProductId(int productId) {
        return repository.findByProductId(productId);
    }
}