package com.danghieu99.monolith.product.repository.jpa;

import com.danghieu99.monolith.product.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Integer> {

    Optional<Variant> findByUuid(UUID uuid);

    @Query("select v from Variant v join Product p on v.productId = p.id where p.uuid = :productUUID")
    Optional<Variant> findByProductUuid(UUID productUUID);

    void deleteByUuid(UUID uuid);
}