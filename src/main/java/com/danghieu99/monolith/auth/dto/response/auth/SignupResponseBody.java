package com.danghieu99.monolith.auth.dto.response.auth;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class SignupResponseBody {

    @NotNull
    private String username;

    @NotNull
    private Set<String> roles;

    private String message;
}
