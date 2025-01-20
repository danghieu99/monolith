package com.danghieu99.monolith.product.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    private Product product;

    @ManyToMany
    @JoinTable(
            name = "variant_attributes",
            joinColumns = @JoinColumn(name = "variant_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id", referencedColumnName = "id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"attribute_type_id"})}
    )
    @ToString.Exclude
    private Set<Attribute> attributes = new HashSet<>();

    private String sku;

    private String imgUrl;
}