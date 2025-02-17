package com.danghieu99.monolith.security.dto.auth.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import jakarta.validation.constraints.NotNull;

import java.util.List;

@Data
@Builder
public class LoginResponse {

    @NotNull
    private LoginResponseBody body;

    @NotNull
    HttpHeaders headers;
}
