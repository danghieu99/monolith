package com.danghieu99.monolith.product.service.product;

import com.danghieu99.monolith.product.entity.ProductCategory;
import com.danghieu99.monolith.product.repository.jpa.ProductCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ProductCategoryCrudService {

    private final ProductCategoryRepository repository;

    @Transactional
    public Collection<ProductCategory> saveAll(Collection<ProductCategory> categories) {
        return repository.saveAll(categories);
    }
}
