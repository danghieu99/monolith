package com.danghieu99.monolith.security.repository.jpa;

import com.danghieu99.monolith.security.entity.Account;
import com.danghieu99.monolith.security.constant.ERole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findById(int id);

    Optional<Account> findByUsername(String username);

    @Query("select a from Account a where a.username like concat('%',:username, '%')")
    Page<Account> findByUsernameContaining(String username, Pageable pageable);

    Optional<Account> findByUuid(UUID uuid);

    Optional<Account> findByEmail(String email);

    @Query("select a from Account a where a.email like concat('%', :email, '%')")
    Page<Account> findByEmailContains(String email, Pageable pageable);

    Optional<Account> findByPhone(String phone);

    @Query("select a from Account a where a.phone like concat('%', :phone, '%')")
    Page<Account> findByPhoneContaining(String phone, Pageable pageable);

    @Query("select a from Account a where a.phone like concat('%', :phone)")
    Page<Account> findByPhoneStartingWith(String phone, Pageable pageable);

    @Query("select a from Account a join AccountRole ar on a.id = ar.accountId join Role r on r = :eRole")
    Page<Account> findByERole(ERole eRole, Pageable pageable);
}