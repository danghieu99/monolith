package com.danghieu99.monolith.auth.service.account;

import com.danghieu99.monolith.auth.enums.ERole;
import com.danghieu99.monolith.auth.repository.jpa.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.danghieu99.monolith.auth.entity.Role;

import jakarta.transaction.Transactional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public boolean existsByRole(ERole role) {
        return roleRepository.findByRole(role).isPresent();
    }

    public Role getByRole(ERole role) {
        return roleRepository.findByRole(role).orElseThrow();
    }

    public Page<Role> getAllPaged(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }
}