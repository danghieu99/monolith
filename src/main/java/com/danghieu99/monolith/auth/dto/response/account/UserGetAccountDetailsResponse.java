package com.danghieu99.monolith.auth.dto.response.account;

import com.danghieu99.monolith.auth.enums.EGender;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;
import java.util.Set;

@Data
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserGetAccountDetailsResponse {

    @NotNull
    private String username;

    @NotNull
    private EGender gender;

    @NotNull
    private String fullName;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @NotNull
    private Set<String> roles;
}
