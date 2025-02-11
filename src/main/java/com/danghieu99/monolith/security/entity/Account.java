package com.danghieu99.monolith.security.entity;

import com.danghieu99.monolith.common.entity.BaseEntity;
import com.danghieu99.monolith.security.constant.EGender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Account extends BaseEntity {

    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;

    @PrePersist
    private void onCreate() {
        this.uuid = UUID.randomUUID();
    }

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EGender gender;

    @Column(nullable = false)
    private String fullName;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;
}