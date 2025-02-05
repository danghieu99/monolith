package com.danghieu99.monolith.cart.entity;

import com.danghieu99.monolith.cart.entity.value.SavedCartItem;
import com.danghieu99.monolith.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
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

    @Column(nullable = false, unique = true)
    @NotNull
    private int userId;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<SavedCartItem> items = new ArrayList<>();

}
