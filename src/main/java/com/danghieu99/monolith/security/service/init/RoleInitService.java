package com.danghieu99.monolith.security.service.init;

import com.danghieu99.monolith.security.entity.Role;
import com.danghieu99.monolith.security.constant.ERole;
import com.danghieu99.monolith.security.service.account.RoleCrudService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleInitService {

    private final RoleCrudService roleCrudService;

    public RoleInitService(RoleCrudService roleCrudService) {
        this.roleCrudService = roleCrudService;
    }

    @Transactional
    public void init() {
        Arrays.stream(ERole.values()).forEach(role -> {
            if (!roleCrudService.existsByRole(role)) roleCrudService.save(new Role(role, role.getDescription()));
        });
    }
}