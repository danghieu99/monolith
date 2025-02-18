package com.danghieu99.monolith.product.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "global_product_attributes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GlobalAttributeType extends BaseEntity {

    @Column(nullable = false, updatable = false, unique = true)
    private UUID uuid;

    @PrePersist
    private void prePersist() {
        this.uuid = UUID.randomUUID();
    }

    @Column(nullable = false, unique = true)
    private String name;
}
