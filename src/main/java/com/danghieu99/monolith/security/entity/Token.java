package com.danghieu99.monolith.security.entity;

import jakarta.persistence.Id;
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
    private String id;

    @NotNull
    private Integer userId;

    @NotNull
    private String tokenValue;

    @NotNull
    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private int expiration;
}