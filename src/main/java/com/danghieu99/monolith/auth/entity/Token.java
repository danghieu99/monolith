package com.danghieu99.monolith.auth.entity;

import com.danghieu99.monolith.common.entity.BaseRedisEntity;
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
public class Token extends BaseRedisEntity {

    @NotNull
    private Integer userId;

    @NotNull
    private String tokenValue;

    @NotNull
    @TimeToLive(unit = TimeUnit.MILLISECONDS)
    private int expiration;
}