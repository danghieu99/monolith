package com.danghieu99.monolith.security.service.account;

import com.danghieu99.monolith.security.entity.AccountRole;
import com.danghieu99.monolith.security.repository.jpa.AccountRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AccountRoleService {

    private final AccountRoleRepository accountRoleRepository;

    @Transactional
    public Collection<AccountRole> saveAll(Collection<AccountRole> accountRoles) {
        return accountRoleRepository.saveAll(accountRoles);
    }
}
