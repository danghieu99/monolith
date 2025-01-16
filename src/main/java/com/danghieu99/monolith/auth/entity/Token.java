package com.danghieu99.monolith.auth.entity;

import com.danghieu99.monolith.common.entity.BaseRedisEntity;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@RedisHash("tokens")
public class Token extends BaseRedisEntity {

    private Integer userId;

    private String tokenValue;
}
