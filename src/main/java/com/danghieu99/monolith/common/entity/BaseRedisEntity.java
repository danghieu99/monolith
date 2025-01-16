package com.danghieu99.monolith.common.entity;


import jakarta.persistence.PrePersist;
import lombok.Getter;

import jakarta.persistence.Id;

import java.util.UUID;

@Getter
public abstract class BaseRedisEntity {

    @Id
    private final UUID id;

    public BaseRedisEntity() {
        this.id = UUID.randomUUID();
    }
}