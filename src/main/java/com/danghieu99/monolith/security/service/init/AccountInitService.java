package com.danghieu99.monolith.security.service.init;

import com.danghieu99.monolith.security.entity.Account;
import com.danghieu99.monolith.security.entity.Role;
import com.danghieu99.monolith.security.enums.EGender;
import com.danghieu99.monolith.security.enums.ERole;
import com.danghieu99.monolith.security.service.account.AccountCrudService;
import com.danghieu99.monolith.security.service.account.RoleCrudService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountInitService {

    private final AccountCrudService accountCrudService;
    private final PasswordEncoder passwordEncoder;
    private final RoleCrudService roleCrudService;

    @Transactional
    public void init() {
        if (accountCrudService.getAll().isEmpty()) {
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(roleCrudService.getByERole(ERole.ROLE_USER));
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(roleCrudService.getByERole(ERole.ROLE_ADMIN));
            Set<Role> modRoles = new HashSet<>();
            modRoles.add(roleCrudService.getByERole(ERole.ROLE_MODERATOR));
            Set<Role> sellerRoles = new HashSet<>();
            sellerRoles.add(roleCrudService.getByERole(ERole.ROLE_SELLER));


            IntStream.range(1, 10).parallel().forEach(i -> {
                accountCrudService.create(Account.builder()
                        .username("admin" + i)
                        .password(passwordEncoder.encode("adminpassword" + i))
                        .gender(EGender.valueOf(i % 2 == 0 ? "MALE" : "FEMALE"))
                        .email("admin" + i + "@mail.com")
                        .phone("012345678" + i)
                        .fullName("Admin Full Name " + i)
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
                        .fullName("User Full Name " + i)
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
                        .fullName("Moderator Full Name " + i)
                        .roles(modRoles)
                        .build());
            });


            IntStream.range(1, 20).parallel().forEach(i -> {
                accountCrudService.create(Account.builder()
                        .username("seller" + i)
                        .password(passwordEncoder.encode("sellerpassword" + i))
                        .gender(EGender.valueOf(i % 2 == 0 ? "MALE" : "FEMALE"))
                        .email("seller" + i + "@mail.com")
                        .phone("00100123456" + i)
                        .fullName("Seller Full Name " + i)
                        .roles(sellerRoles)
                        .build());
            });
        }
    }
}