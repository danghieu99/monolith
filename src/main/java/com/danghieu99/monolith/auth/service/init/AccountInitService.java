package com.danghieu99.monolith.auth.service.init;

import com.danghieu99.monolith.auth.entity.Account;
import com.danghieu99.monolith.auth.entity.Role;
import com.danghieu99.monolith.auth.enums.EGender;
import com.danghieu99.monolith.auth.enums.ERole;
import com.danghieu99.monolith.auth.service.account.AccountCrudService;
import com.danghieu99.monolith.auth.service.account.RoleCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AccountInitService {

    private final AccountCrudService accountCrudService;
    private final PasswordEncoder passwordEncoder;
    private final RoleCrudService roleCrudService;

    public void init() {
        if (accountCrudService.getAll().isEmpty()) {

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(roleCrudService.getByERole(ERole.ROLE_USER));
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(roleCrudService.getByERole(ERole.ROLE_ADMIN));
            Set<Role> modRoles = new HashSet<>();
            modRoles.add(roleCrudService.getByERole(ERole.ROLE_MODERATOR));

            IntStream.range(1, 10).parallel().forEach(i -> {
                accountCrudService.create(Account.builder()
                        .username("admin" + i)
                        .password(passwordEncoder.encode("adminpassword" + i))
                        .gender(EGender.valueOf(i % 2 == 0 ? "MALE" : "FEMALE"))
                        .email("admin" + i + "@mail.com")
                        .phone("012345678" + i)
                        .fullName("Admin Full Name" + i)
                        .roles(adminRoles)
                        .build());
            });

            IntStream.range(1, 100).parallel().forEach(i -> {
                accountCrudService.create(Account.builder()
                        .username("user" + i)
                        .password(passwordEncoder.encode("userpassword" + i))
                        .gender(EGender.valueOf(i % 2 == 0 ? "MALE" : "FEMALE"))
                        .email("user" + i + "@mail.com")
                        .phone("023456789" + i)
                        .fullName("User Full Name" + i)
                        .roles(userRoles)
                        .build());
            });

            IntStream.range(1, 20).parallel().forEach(i -> {
                accountCrudService.create(Account.builder()
                        .username("moderator" + i)
                        .password(passwordEncoder.encode("modpassword" + i))
                        .gender(EGender.valueOf(i % 2 == 0 ? "MALE" : "FEMALE"))
                        .email("mod" + i + "@mail.com")
                        .phone("0012345678" + i)
                        .fullName("Moderator Full Name" + i)
                        .roles(modRoles)
                        .build());
            });
        }
    }
}