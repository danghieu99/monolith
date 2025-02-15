package com.danghieu99.monolith.security.service.init;

import com.danghieu99.monolith.security.entity.Role;
import com.danghieu99.monolith.security.constant.ERole;
import com.danghieu99.monolith.security.service.dao.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleInitService {

    private final RoleService roleService;

    public RoleInitService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Transactional
    public void init() {
        Arrays.stream(ERole.values()).forEach(role -> {
            if (!roleService.existsByRole(role)) roleService.save(new Role(role, role.getDescription()));
        });
    }
}