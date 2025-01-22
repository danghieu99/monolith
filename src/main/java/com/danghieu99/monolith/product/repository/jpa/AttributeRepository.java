package com.danghieu99.monolith.product.repository.jpa;

import com.danghieu99.monolith.product.entity.Attribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

    @Query("select a from Attribute a where a.product.id = :productId group by a.type")
    List<Attribute> findAttributesByProductIdGroupByAttributeType(int productId);

    @Query("select a from Attribute a where a.product.id = :productId group by a.type")
    Page<Attribute> findAttributesByProductIdGroupByAttributeType(int productId, Pageable pageable);

}
