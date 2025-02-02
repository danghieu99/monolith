package com.danghieu99.monolith.product.service.variant;

import com.danghieu99.monolith.product.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Integer> {


}
