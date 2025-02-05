package com.danghieu99.monolith.security.repository.jpa;

import com.danghieu99.monolith.security.entity.Role;
import com.danghieu99.monolith.security.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(ERole role);
}