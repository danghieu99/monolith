package com.danghieu99.monolith.order.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "order_items",
        uniqueConstraints = {@UniqueConstraint(columnNames = "order_id, product_id, variant_id")})
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
    private UUID productUUID;

    @Column(nullable = false)
    private UUID variantUUID;

    @Column(nullable = false)
    private int quantity;
}
