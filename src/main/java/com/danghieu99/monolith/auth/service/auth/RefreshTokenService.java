package com.danghieu99.monolith.auth.service.auth;

import com.danghieu99.monolith.auth.entity.Token;
import com.danghieu99.monolith.auth.redisrepository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RefreshTokenService {

    private final TokenRepository repository;

    public RefreshTokenService(TokenRepository repository) {
        this.repository = repository;
    }

    public Token save(Token entity) {
        return repository.save(entity);
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

    public boolean updateByUserId(Integer id, Token token) {
        if (repository.existsByUserId(id)) {
            repository.save(token);
            return true;
        }
        return false;
    }

    public void deleteByUserId(Integer id) {
        repository.deleteByUserId(id);
    }

    public void deleteByValue(String value) {
        repository.deleteByTokenValue(value);
    }
}