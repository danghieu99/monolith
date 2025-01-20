package com.danghieu99.monolith.product.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Product extends BaseEntity {

    private UUID uuid;

    @PrePersist
    protected void onCreate() {
        this.uuid = UUID.randomUUID();
    }

    private String name;

    private String description;

    @ManyToMany
    @ToString.Exclude
    private Set<Category> categories = new HashSet<>();

    @ManyToOne(optional = false)
    private Shop shop;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<Attribute> attributeTypes = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private Set<Variant> variants = new HashSet<>();

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}