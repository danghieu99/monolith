package com.danghieu99.monolith.product.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "variants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Variant extends BaseEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ElementCollection
    @CollectionTable(name = "variant_attributes",
            joinColumns = @JoinColumn(name = "variant_id", nullable = false),
            uniqueConstraints = @UniqueConstraint(name = "uq_variant_attribute_type", columnNames = {"variant_id", "attribute_type"}),
            indexes = {@Index(name = "attributes_idx", columnList = "attribute_type, attribute_value")})
    @MapKeyColumn(name = "attribute_type")
    @Column(name = "attribute_value")
    @ToString.Exclude
    private Map<String, String> attributes = new HashMap<>();

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int stock;
}