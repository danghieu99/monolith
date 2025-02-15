package com.danghieu99.monolith.product.repository.jpa;

import com.danghieu99.monolith.product.entity.VariantAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantAttributeRepository extends JpaRepository<VariantAttribute, Integer> {

}
