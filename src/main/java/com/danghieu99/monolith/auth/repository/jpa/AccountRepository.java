package com.danghieu99.monolith.auth.repository.jpa;

import com.danghieu99.monolith.auth.entity.Account;
import com.danghieu99.monolith.auth.entity.Role;
import com.danghieu99.monolith.auth.enums.ERole;
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

    @Query("select a from Account a join a.roles r where r.role = :eRole")
    Page<Account> findByERole(ERole eRole, Pageable pageable);

    @Query("select a from Account a join a.roles r where r.role = :role")
    Page<Account> findByRolesContaining(ERole role, Pageable pageable);
}