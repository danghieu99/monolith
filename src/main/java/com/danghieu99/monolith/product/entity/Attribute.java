package com.danghieu99.monolith.product.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attributes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Attribute extends BaseEntity {

    @ManyToOne(optional = false)
    private Product product;

    @ToString.Exclude
    @JoinColumn
    private String type;

    @ToString.Exclude
    private String value;
}
