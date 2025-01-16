package com.danghieu99.monolith.auth.dto.request.auth;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@EqualsAndHashCode(callSuper = false)
@Data
public class LoginRequest {

    @NotNull
    @Size(min = 3, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$")
    private String username;

    @NotNull
    @Size(min = 8, max = 16)
    private String password;
}