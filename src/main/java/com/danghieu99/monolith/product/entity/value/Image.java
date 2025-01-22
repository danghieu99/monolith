package com.danghieu99.monolith.product.entity.value;

import com.danghieu99.monolith.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Image extends BaseEntity {
    private String url;

    private String fileName;
}
