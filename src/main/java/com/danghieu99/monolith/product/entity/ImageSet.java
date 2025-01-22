package com.danghieu99.monolith.product.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import com.danghieu99.monolith.product.entity.value.Image;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "image_sets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ImageSet extends BaseEntity {

    @OneToOne
    private Product product;

    private Image thumbnail;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Image> gallery;
}
