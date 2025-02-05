package com.danghieu99.monolith.security.dto.auth.response;

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
