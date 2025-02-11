package com.danghieu99.monolith.product.repository.jpa;

import com.danghieu99.monolith.product.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {

    Optional<Shop> findByName(String name);

    Optional<Shop> findByUuid(UUID uuid);

    @Query("select s from Shop s join Product p on s.id = p.shopId where p.uuid = :productUUID")
    Optional<Shop> findByProductUuid(UUID productUUID);
}
