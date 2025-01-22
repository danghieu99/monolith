package com.danghieu99.monolith.product.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import com.danghieu99.monolith.product.entity.value.Image;
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
public class Product extends BaseEntity {

    @Builder
    public Product(String name, String description, Set<Category> categories, Shop shop, List<Attribute> attributes, Set<Variant> variants, InternalFlag internalFlag) {
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.shop = shop;
        this.attributes = attributes;
        this.variants = variants;
        this.internalFlag = internalFlag;
    }

    private UUID uuid;

    @PrePersist
    private void onCreate() {
        this.uuid = UUID.randomUUID();
    }

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "products")
    @ToString.Exclude
    private Set<Category> categories = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "shop_id", referencedColumnName = "id", nullable = false)
    private Shop shop;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<Attribute> attributes = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private Set<Variant> variants = new HashSet<>();

    @OneToOne
    private ImageSet imageSet;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @OneToOne
    private InternalFlag internalFlag;
}