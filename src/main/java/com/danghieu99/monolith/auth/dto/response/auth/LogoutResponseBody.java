package com.danghieu99.monolith.auth.dto.response.auth;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class LogoutResponseBody {

    private String message;
}
