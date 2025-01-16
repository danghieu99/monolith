package com.danghieu99.monolith.auth.dto.response.auth;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class LogoutResponse {

    private LogoutResponseBody body;
}
