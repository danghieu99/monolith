package com.danghieu99.monolith.security.service.dao;

import com.danghieu99.monolith.security.constant.ERole;
import com.danghieu99.monolith.security.entity.Account;
import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.security.repository.jpa.AccountRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    public List<Account> getAll() {
        return repository.findAll();
    }

    public Page<Account> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Account> getByRole(ERole role) {
        return repository.findByERole(role);
    }

    public Account getById(@NotNull Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "Id", id));
    }

    public Account getByUsername(@NotNull String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "Username", username));
    }

    public Account getByEmail(@NotNull String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "Email", email));
    }

    public Account getByPhone(@NotNull String phone) {
        return repository.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "Phone", phone));
    }

    public Account getById(@NotNull int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "Id", id));
    }

    public Account getByUUID(@NotNull UUID uuid) {
        return repository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "Uuid", uuid.toString()));
    }

    @Transactional
    public Account save(@NotNull Account account) {
        if (account.getId() != null) {
            throw new IllegalArgumentException("New account id must be null");
        }
        return repository.save(account);
    }

    @Transactional
    public Collection<Account> saveAll(@NotNull Collection<Account> accounts) {
        return repository.saveAll(accounts);
    }

    @Transactional
    public Account update(@NotNull Account account) {
        if (account.getId() == null) {
            throw new IllegalArgumentException("Update account id must not be null");
        }
        return repository.save(account);
    }

    @Transactional
    public void deleteById(@NotNull int id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Account", "Id", id);
        }
        repository.deleteById(id);
    }
}