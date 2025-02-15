package com.danghieu99.monolith.order.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import com.danghieu99.monolith.order.constant.EOrderStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Order extends BaseEntity {

    @Column(nullable = false)
    private int shopId;

    @Column(nullable = false)
    private int userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EOrderStatus status;
}