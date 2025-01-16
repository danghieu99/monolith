package com.danghieu99.monolith.product.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "variant_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class VariantType extends BaseEntity {

    @ManyToOne(optional = false)
    private Product product;

    @OneToMany(mappedBy = "type")
    @ToString.Exclude
    private Set<VariantValue> values;
}