package com.danghieu99.monolith.order.repository;

import com.danghieu99.monolith.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByAccountId(int id);

    @Query("select o from Order o " +
            "join Account a on o.accountId = a.id " +
            "where a.uuid = :uuid")
    List<Order> findByUserUUID(UUID uuid);

    Optional<Order> findByUuid(UUID uuid);
}
