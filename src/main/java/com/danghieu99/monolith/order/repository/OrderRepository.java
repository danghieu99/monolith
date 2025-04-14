package com.danghieu99.monolith.order.repository;

import com.danghieu99.monolith.order.constant.EOrderStatus;
import com.danghieu99.monolith.order.entity.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserAccountUUID(String uuid);

    Optional<Order> findByUuid(UUID uuid);

    @Modifying
    @Transactional
    @Query("update Order o " +
            "set o.status = :status " +
            "where o.uuid = :uuid")
    void updateOrderStatus(UUID uuid, EOrderStatus status);
}
