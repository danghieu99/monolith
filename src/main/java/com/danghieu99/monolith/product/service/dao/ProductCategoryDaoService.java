package com.danghieu99.monolith.product.service.dao;

import com.danghieu99.monolith.product.entity.ProductCategory;
import com.danghieu99.monolith.product.repository.jpa.ProductCategoryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductCategoryDaoService {

    private final ProductCategoryRepository repository;

    @Transactional
    public ProductCategory save(@NotNull ProductCategory productCategory) {
        return repository.save(productCategory);
    }

    @Transactional
    public Collection<ProductCategory> saveAll(@NotEmpty Collection<ProductCategory> categories) {
        return repository.saveAll(categories);
    }

    public Set<ProductCategory> getByProductId(@NotNull int productId) {
        return repository.findByProductId(productId);
    }

    public Set<ProductCategory> findByProductUuid(@NotNull UUID productUUID) {
        return repository.findByProductUuid(productUUID);
    }
}