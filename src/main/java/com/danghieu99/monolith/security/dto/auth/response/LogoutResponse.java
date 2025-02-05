package com.danghieu99.monolith.security.dto.auth.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class LogoutResponse {

    private LogoutResponseBody body;
}
