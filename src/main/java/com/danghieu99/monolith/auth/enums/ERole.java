package com.danghieu99.monolith.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ERole {
    ROLE_USER("User Role"),
    ROLE_ADMIN("Admin Role"),
    ROLE_MODERATOR("Moderator Role"),
    ROLE_SELLER("Seller Role");

    private final String description;
}