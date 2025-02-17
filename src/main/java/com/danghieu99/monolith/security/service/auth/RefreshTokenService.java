package com.danghieu99.monolith.security.service.auth;

import com.danghieu99.monolith.security.config.auth.AuthTokenProperties;
import com.danghieu99.monolith.security.entity.Token;
import com.danghieu99.monolith.security.repository.redis.TokenRepository;
import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private final TokenRepository repository;

    private final AuthTokenProperties authTokenProperties;

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

    @Transactional
    public Token updateByUserId(Token token) {
        if (!repository.existsByUserId(token.getUserId())) {
            throw new ResourceNotFoundException("Token", "userId", token.getUserId());
        }
        return repository.save(token);
    }

    @Transactional
    public void deleteByUserId(Integer id) {
        repository.deleteByUserId(id);
    }

    @Transactional
    public void deleteByValue(String value) {
        repository.deleteByTokenValue(value);
    }
}