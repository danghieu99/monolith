package com.danghieu99.monolith.product.repository.jpa;

import com.danghieu99.monolith.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    Collection<ProductCategory> findByProductId(Integer productId);
}
