package com.danghieu99.monolith.auth.service.token;

import com.danghieu99.monolith.auth.config.authentication.TokenProperties;
import com.danghieu99.monolith.auth.entity.Token;
import com.danghieu99.monolith.auth.repository.redis.TokenRepository;
import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private final TokenRepository repository;

    private final TokenProperties tokenProperties;

    public Token save(Token token) {
        return repository.save(token);
    }

    public boolean existsByValue(String value) {
        return repository.existsByTokenValue(value);
    }

    public boolean existsByUserId(int userId) {
        return repository.existsByUserId(userId);
    }

    public Optional<Token> findByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    public Optional<Token> findByValue(String value) {
        return repository.findByTokenValue(value);
    }

    public Token updateByUserId(Token token) {
        if (!repository.existsByUserId(token.getUserId())) {
            throw new ResourceNotFoundException("Token", "userId", token.getUserId());
        }
        return repository.save(token);
    }

    public void deleteByUserId(Integer id) {
        repository.deleteByUserId(id);
    }

    public void deleteByValue(String value) {
        repository.deleteByTokenValue(value);
    }
}