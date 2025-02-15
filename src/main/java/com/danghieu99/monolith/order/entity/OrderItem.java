package com.danghieu99.monolith.order.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class OrderItem extends BaseEntity {

    @Column(nullable = false)
    private int orderId;

    @Column(nullable = false)
    private int itemId;
}
