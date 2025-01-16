package com.danghieu99.monolith.auth.redisrepository;

import com.danghieu99.monolith.auth.entity.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TokenRepository extends CrudRepository<Token, Integer> {
    boolean existsByTokenValue(String token);

    boolean existsByUserId(int userId);

    void deleteByUserId(int userId);

    Optional<Token> findByTokenValue(String token);

    Optional<Token> findByUserId(int userId);

    void deleteByTokenValue(String token);
}
