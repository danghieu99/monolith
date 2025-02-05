package com.danghieu99.monolith.cart.repository.jpa;

import com.danghieu99.monolith.cart.entity.SavedCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavedCartRepository extends JpaRepository<SavedCart, Integer> {

    Optional<SavedCart> findByUserId(Integer userId);
}
