package com.danghieu99.monolith.auth.dto.request.account;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
public class UserChangePasswordRequest {

    @NotNull
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$")
    private String oldPassword;

    @NotNull
    @Size(min = 8, max = 20)
    private String newPassword;
}
