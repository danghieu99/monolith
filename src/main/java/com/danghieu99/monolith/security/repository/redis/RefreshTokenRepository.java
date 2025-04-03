package com.danghieu99.monolith.security.repository.redis;

import com.danghieu99.monolith.security.entity.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends CrudRepository<Token, UUID> {
    boolean existsByTokenValue(String token);

    boolean existsByAccountUUID(String accountUUID);

    Optional<Token> findByAccountUUID(String accountUUID);

    Optional<Token> findByTokenValue(String token);

    void deleteByTokenValue(String token);

    void deleteByAccountUUID(String accountUUID);
}