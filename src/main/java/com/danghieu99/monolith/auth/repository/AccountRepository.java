package com.danghieu99.monolith.auth.repository;

import com.danghieu99.monolith.auth.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);

    Optional<Account> findByUuid(UUID uuid);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByPhone(String phone);

    Optional<Account> findById(int id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    @Query("SELECT a FROM Account a WHERE a.username LIKE CONCAT('%',:username, '%')")
    Page<Account> searchByUsername(String username, Pageable pageable);
}