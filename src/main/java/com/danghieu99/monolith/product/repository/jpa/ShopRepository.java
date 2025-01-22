package com.danghieu99.monolith.product.repository.jpa;

import com.danghieu99.monolith.product.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {

    Optional<Shop> findByName(String name);

    @Query("select s from Shop s join s.products sp where sp.uuid = :productUuid")
    Optional<Shop> findByProductUuid(String productUuid);


}
