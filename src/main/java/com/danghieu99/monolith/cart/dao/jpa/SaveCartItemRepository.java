package com.danghieu99.monolith.cart.dao.jpa;

import com.danghieu99.monolith.cart.entity.SaveCartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaveCartItemRepository extends JpaRepository<SaveCartItem, Integer> {

    List<SaveCartItem> findByUserId(Integer userId);
}
