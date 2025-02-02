package com.danghieu99.monolith.auth.entity;


import com.danghieu99.monolith.common.entity.BaseEntity;
import com.danghieu99.monolith.auth.enums.ERole;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ERole role;

    @Column(nullable = false)
    private String description;
}