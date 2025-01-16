package com.danghieu99.monolith.common.entity;

import lombok.Getter;

import jakarta.persistence.*;

import jakarta.persistence.MappedSuperclass;

import java.io.Serializable;

@MappedSuperclass
@Getter
public abstract class BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false, insertable = false)
    private Integer id;
}