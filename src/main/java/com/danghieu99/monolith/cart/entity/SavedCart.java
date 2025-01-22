package com.danghieu99.monolith.cart.entity;

import com.danghieu99.monolith.cart.dto.CartItemDto;
import com.danghieu99.monolith.common.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "saved_carts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SavedCart extends BaseEntity {

    private int userId;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<CartItemDto> items;
}
