package com.danghieu99.monolith.cart.repository.jpa;

import com.danghieu99.monolith.cart.entity.SavedCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavedCartRepository extends JpaRepository<SavedCart, Integer> {


}
