package com.danghieu99.monolith.product.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.ValidationException;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
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
    public Product(String name,
                   String description,
                   Set<Category> categories,
                   Shop shop) {
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.shop = shop;
    }

    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @PrePersist
    private void prePersist() {
        this.uuid = UUID.randomUUID();
        validateVariants();
    }

    @PreUpdate
    private void preUpdate() {
        validateVariants();
    }

    private void validateVariants() {
        if (variants.isEmpty()) {
            throw new ValidationException("Product requires at least one Variant");
        }
    }

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id", nullable = false)
    private Shop shop;

    @ManyToMany
    @JoinTable(name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "category_id", nullable = false))
    @ToString.Exclude
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Variant> variants = new HashSet<>();

    @CreationTimestamp
    @Setter(AccessLevel.NONE)
    private Instant createdAt;

    @UpdateTimestamp
    @Setter(AccessLevel.NONE)
    private Instant updatedAt;

    @Column(nullable = false)
    private BigDecimal price;
}