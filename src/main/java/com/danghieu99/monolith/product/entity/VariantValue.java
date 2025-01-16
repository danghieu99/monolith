package com.danghieu99.monolith.product.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "variant_values")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class VariantValue extends BaseEntity {

    @ManyToOne(optional = false, targetEntity = ProductVariant.class)
    private VariantType type;

    private String value;
}
