package com.danghieu99.monolith.product.repository.jpa;

import com.danghieu99.monolith.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    Set<ProductCategory> findByProductId(Integer productId);

    @Query("select pc from ProductCategory pc join Product p on pc.productId = p.id where p.uuid = :uuid")
    Set<ProductCategory> findByProductUuid(UUID productUUID);
}
