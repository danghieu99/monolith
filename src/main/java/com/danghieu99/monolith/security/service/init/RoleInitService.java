package com.danghieu99.monolith.security.service.init;

import com.danghieu99.monolith.security.entity.Role;
import com.danghieu99.monolith.security.constant.ERole;
import com.danghieu99.monolith.security.service.dao.RoleDaoService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleInitService {

    private final RoleDaoService roleDaoService;

    public RoleInitService(RoleDaoService roleDaoService) {
        this.roleDaoService = roleDaoService;
    }

    @Transactional
    public void init() {
        Arrays.stream(ERole.values()).forEach(role -> {
            if (!roleDaoService.existsByRole(role)) roleDaoService.save(new Role(role, role.getDescription()));
        });
    }
}