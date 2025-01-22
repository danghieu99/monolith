package com.danghieu99.monolith.auth.service.init;

import com.danghieu99.monolith.auth.entity.Role;
import com.danghieu99.monolith.auth.enums.ERole;
import com.danghieu99.monolith.auth.service.account.RoleCrudService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleInitService {

    private final RoleCrudService roleCrudService;

    public RoleInitService(RoleCrudService roleCrudService) {
        this.roleCrudService = roleCrudService;
    }

    public void init() {
        if (roleCrudService.getAll().isEmpty()) {
            Arrays.stream(ERole.values()).forEach(role -> {
                if (!roleCrudService.existsByRole(role)) roleCrudService.save(new Role(role, role.getDescription()));
            });
        }
    }
}