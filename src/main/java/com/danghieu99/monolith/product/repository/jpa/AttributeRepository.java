package com.danghieu99.monolith.product.repository.jpa;

import com.danghieu99.monolith.product.entity.Attribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Integer> {

    @Query("select a from Attribute a join Product p on a.productId = p.id where p.uuid = :uuid")
    List<Attribute> findByProductUUID(UUID productUUID);

    @Query("select a from Attribute a join Product p on a.productId = p.id where p.uuid = :uuid and a.type = :type")
    List<Attribute> findByProductUUIDAndTypeIgnoreCase(UUID productUUID, String type);

    @Query("select a from Attribute a join Product p on a.productId = p.id where p.uuid = :uuid and a.type in :types")
    List<Attribute> findByProductUUIDAndTypeIgnoreCaseIn(UUID productUUID, List<String> types);

    Page<Attribute> findByTypeAndValueContainingIgnoreCase(String type, String value, Pageable pageable);
}