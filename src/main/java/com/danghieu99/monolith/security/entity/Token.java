package com.danghieu99.monolith.security.entity;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@RedisHash("tokens")
public class Token {

    @Id
    @NotBlank
    private String tokenValue;

    @NotBlank
    private String accountUUID;

    @NotNull
    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private long expiration;
}