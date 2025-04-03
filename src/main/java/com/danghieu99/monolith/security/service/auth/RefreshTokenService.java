package com.danghieu99.monolith.security.service.auth;

import com.danghieu99.monolith.security.entity.Token;
import com.danghieu99.monolith.security.repository.redis.RefreshTokenRepository;
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

    private final RefreshTokenRepository repository;

    public Token save(Token token) {
        return repository.save(token);
    }

    public boolean existsByValue(String value) {
        return repository.existsByTokenValue(value);
    }

    public Optional<Token> findByValue(String value) {
        return repository.findByTokenValue(value);
    }

    public void deleteByAccountUUID(String accountUUID) {
        repository.deleteByAccountUUID(accountUUID);
    }

    @Transactional
    public void deleteByValue(String value) {
        repository.deleteByTokenValue(value);
    }
}