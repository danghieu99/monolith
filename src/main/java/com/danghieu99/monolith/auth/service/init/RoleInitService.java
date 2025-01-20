package com.danghieu99.monolith.auth.service.init;

import com.danghieu99.monolith.auth.entity.Role;
import com.danghieu99.monolith.auth.enums.ERole;
import com.danghieu99.monolith.auth.service.account.RoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleInitService {

    private final RoleService roleService;

    public RoleInitService(RoleService roleService) {
        this.roleService = roleService;
    }

    public void init() {
        Arrays.stream(ERole.values()).forEach(role -> {
            if (!roleService.existsByRole(role)) roleService.save(new Role(role, role.getDescription()));
        });
    }
}