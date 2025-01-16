package com.danghieu99.monolith.product.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Map;

@Entity
@Table(name = "variants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductVariant extends BaseEntity {


    Map<VariantType, VariantValue> variants;

}
