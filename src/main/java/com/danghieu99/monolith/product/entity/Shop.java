package com.danghieu99.monolith.product.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import com.danghieu99.monolith.product.constant.EShopStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "shops")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Shop extends BaseEntity {

    @Setter(AccessLevel.NONE)
    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;

    @PrePersist
    protected void onCreate() {
        this.uuid = UUID.randomUUID();
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, unique = true, updatable = false)
    private int accountId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EShopStatus status;
}
