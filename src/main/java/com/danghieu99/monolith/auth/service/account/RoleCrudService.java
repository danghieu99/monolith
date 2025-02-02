package com.danghieu99.monolith.auth.service.account;

import com.danghieu99.monolith.auth.enums.ERole;
import com.danghieu99.monolith.auth.repository.jpa.RoleRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.danghieu99.monolith.auth.entity.Role;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class RoleCrudService {

    private final RoleRepository roleRepository;

    public RoleCrudService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @CacheEvict("roles")
    public Role update(Role role) {
        return roleRepository.save(role);
    }

    @CacheEvict("roles")
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    @Cacheable("roles")
    public Role getByERole(ERole role) {
        return roleRepository.findByRole(role).orElseThrow();
    }

    @Cacheable("roles")
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Page<Role> getAllPaged(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public boolean existsByRole(ERole role) {
        return roleRepository.findByRole(role).isPresent();
    }
}