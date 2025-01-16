package com.danghieu99.monolith.auth.dto.request.auth;

import lombok.*;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@EqualsAndHashCode(callSuper = false)
@Data
public class SignupRequest {

    @NotNull
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$")
    private String username;

    @NotNull
    @Size(min = 8, max = 20)
    private String password;

    @NotNull
    @Size(min = 5, max = 255)
    @Pattern(regexp = "^[\\p{L}\\p{M}'\\-.\\s]$")
    private String fullName;

    @NotNull
    @Size(min = 3, max = 100)
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String gender;

    @NotNull
    @Size(min = 5, max = 255)
    @Email
    private String email;

    @NotNull
    @Size(min = 3, max = 25)
    @Pattern(regexp = "^\\+?(\\d{1,4})?[\\s.-]?\\(?\\d{1,4}\\)?[\\s.-]?\\d{1,4}[\\s.-]?\\d{1,9}(?:\\s?(?:ext|x)\\s?\\d{1,5})?$")
    private String phone;
}