package com.danghieu99.monolith.auth.dto.response.account;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEditAccountResponse {
    private boolean success;
    private String message;
}
