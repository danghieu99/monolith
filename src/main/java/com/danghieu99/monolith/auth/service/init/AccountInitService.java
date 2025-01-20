package com.danghieu99.monolith.auth.service.init;

import com.danghieu99.monolith.auth.entity.Account;
import com.danghieu99.monolith.auth.entity.Role;
import com.danghieu99.monolith.auth.enums.EGender;
import com.danghieu99.monolith.auth.enums.ERole;
import com.danghieu99.monolith.auth.service.account.AccountService;
import com.danghieu99.monolith.auth.service.account.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountInitService {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public void init() {
        if (accountService.getAll().isEmpty()) {
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(roleService.getByRole(ERole.ROLE_ADMIN));
            accountService.create(Account.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("adminpassword"))
                    .gender(EGender.MALE)
                    .email("admin@mail.com")
                    .phone("123456789")
                    .fullName("Admin Full Name")
                    .roles(adminRoles)
                    .build());

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(roleService.getByRole(ERole.ROLE_USER));
            accountService.create(Account.builder()
                    .username("user")
                    .password(passwordEncoder.encode("userpassword"))
                    .gender(EGender.MALE)
                    .email("user@mail.com")
                    .phone("987654321")
                    .fullName("User Full Name")
                    .roles(userRoles)
                    .build());
        }
    }
}