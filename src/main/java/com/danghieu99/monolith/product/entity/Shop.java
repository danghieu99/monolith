package com.danghieu99.monolith.product.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import com.danghieu99.monolith.product.enums.EShopStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "sellers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Shop extends BaseEntity {

    private UUID uuid;

    @PrePersist
    protected void onCreate() {
        this.uuid = UUID.randomUUID();
    }

    private String name;

    private String description;

    private EShopStatus status;

    @OneToMany(mappedBy = "shop")
    @ToString.Exclude
    private Set<Product> products;

}
