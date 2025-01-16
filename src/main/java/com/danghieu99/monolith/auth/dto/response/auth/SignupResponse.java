package com.danghieu99.monolith.auth.dto.response.auth;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

@ToString
@Data
@Builder
public class SignupResponse {

    @NotNull
    private SignupResponseBody body;

}
