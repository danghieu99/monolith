package com.danghieu99.monolith.product.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "variants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Variant extends BaseEntity {

    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;

    @PrePersist
    protected void prePersist() {
        uuid = UUID.randomUUID();
    }

    @Column(nullable = false)
    private int productId;

    @ElementCollection
    @CollectionTable(name = "variant_attributes",
            joinColumns = @JoinColumn(name = "variant_id"),
            uniqueConstraints = @UniqueConstraint(name = "uq_variant_attribute_type",
                    columnNames = {"variant_id", "attribute_type"})
    )
    @MapKeyColumn(name = "attribute_type")
    @Column(name = "attribute_value", nullable = false)
    private Map<String, String> attributes;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int stock;
}