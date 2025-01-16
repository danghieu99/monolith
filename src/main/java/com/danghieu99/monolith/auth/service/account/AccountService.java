package com.danghieu99.monolith.auth.service.account;

import com.danghieu99.monolith.auth.entity.Account;
import com.danghieu99.monolith.auth.exception.ResourceNotFoundException;
import com.danghieu99.monolith.auth.repository.AccountRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;

    public Account save(@NotNull final Account account) {
        return repository.save(account);
    }

    public List<Account> getAll() {
        return repository.findAll();
    }

    public Page<Account> getAllPaged(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Account> getFirstPage() {
        return repository.findAll(PageRequest.of(0, 50));
    }

    public Account getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "Id", id));
    }

    public Page<Account> searchByUsernamePaged(@NotNull String username, Pageable pageable) {
        return repository.searchByUsername(username, pageable);
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
    public Account create(@NotNull Account account) {
        if (account.getId() != null) {
            throw new IllegalArgumentException("New account id must be null");
        }
        return repository.save(account);
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