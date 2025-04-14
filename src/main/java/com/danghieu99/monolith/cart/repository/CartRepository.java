package com.danghieu99.monolith.cart.repository;

import com.danghieu99.monolith.cart.entity.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart, String> {


    Optional<Cart> findByAccountUUID(String accountUUID);
}
