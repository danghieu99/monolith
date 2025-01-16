package com.danghieu99.monolith.auth.enums;

import lombok.Getter;

@Getter
public enum ERole {
    ROLE_USER("User Role"),
    ROLE_ADMIN("Admin Role"),
    ROLE_MODERATOR("Moderator Role");

    private final String description;

    ERole(String description) {
        this.description = description;
    }
}