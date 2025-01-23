package com.danghieu99.monolith.auth.entity;


import com.danghieu99.monolith.common.entity.BaseEntity;
import com.danghieu99.monolith.auth.enums.ERole;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Role extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private ERole role;

    @Size(min = 3, max = 255)
    @Column(nullable = false)
    private String description;
}