package com.danghieu99.monolith.order.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import com.danghieu99.monolith.order.constant.EOrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

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
    private String shopUUID;

    @Column(nullable = false)
    private String userAccountUUID;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EOrderStatus status = EOrderStatus.ORDER_PENDING;
}