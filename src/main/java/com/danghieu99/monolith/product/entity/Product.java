package com.danghieu99.monolith.product.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product extends BaseEntity {

    @Builder
    public Product(String name, String description, int shopId, BigDecimal basePrice) {
        this.name = name;
        this.description = description;
        this.shopId = shopId;
        this.basePrice = basePrice;
    }

    @Setter(AccessLevel.NONE)
    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @PrePersist
    private void prePersist() {
        this.uuid = UUID.randomUUID();
    }

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int shopId;

    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private Instant createdAt;

    @UpdateTimestamp
    @Setter(AccessLevel.NONE)
    private Instant updatedAt;

    @Column(nullable = false)
    private BigDecimal basePrice;
}