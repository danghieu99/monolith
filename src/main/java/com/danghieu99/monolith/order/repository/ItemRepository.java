package com.danghieu99.monolith.order.repository;

import com.danghieu99.monolith.order.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByOrderId(Integer orderId);

    @Query("select i from Item i " +
            "join Order o on o.id = i.orderId " +
            "where o.uuid = :orderUUID")
    List<Item> findByOrderUUID(UUID orderUUID);
}
