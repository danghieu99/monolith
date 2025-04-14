package com.danghieu99.monolith.cart.repository;

import com.danghieu99.monolith.cart.entity.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart, String> {

    Cart findByAccountUUID(String accountUUID);
}
