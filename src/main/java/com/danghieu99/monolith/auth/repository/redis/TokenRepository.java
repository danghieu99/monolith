package com.danghieu99.monolith.auth.repository.redis;

import com.danghieu99.monolith.auth.entity.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends CrudRepository<Token, UUID> {
    boolean existsByTokenValue(String token);

    boolean existsByUserId(int userId);

    void deleteByUserId(int userId);

    Optional<Token> findByTokenValue(String token);

    Optional<Token> findByUserId(int userId);

    void deleteByTokenValue(String token);
}
