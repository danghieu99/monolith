package com.danghieu99.monolith.security.service.account;

import com.danghieu99.monolith.security.constant.ERole;
import com.danghieu99.monolith.security.dto.account.request.AdminSaveAccountRequest;
import com.danghieu99.monolith.security.dto.account.response.AdminSaveAccountResponse;
import com.danghieu99.monolith.security.entity.Account;
import com.danghieu99.monolith.security.entity.AccountRole;
import com.danghieu99.monolith.security.mapper.AccountMapper;
import com.danghieu99.monolith.security.service.dao.AccountService;
import com.danghieu99.monolith.security.service.dao.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminAccountService {

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final RoleService roleService;
    private final AccountRoleService accountRoleService;

    @Transactional
    public AdminSaveAccountResponse addAccount(AdminSaveAccountRequest request) {
        var newAccount = accountService.save(accountMapper.toAccount(request));
        Set<AccountRole> accountRoles = new HashSet<>();
        for (var role : request.getRoles()) {
            accountRoles.add(AccountRole.builder().roleId(roleService.getByERole(role).getId()).accountId(newAccount.getId()).build());
        }
        accountRoleService.saveAll(accountRoles);
        return accountMapper.toAdminSaveAccountResponse(accountService.save(newAccount));
    }

    @Transactional
    public AdminSaveAccountResponse updateAccount(int id, AdminSaveAccountRequest request) {
        Account account = accountService.getById(id);
        if (request.getUsername() != null) account.setUsername(request.getUsername());
        if (request.getPassword() != null) account.setPassword(request.getPassword());
        if (request.getEmail() != null) account.setEmail(request.getEmail());
        if (request.getGender() != null) account.setGender(request.getGender());
        if (request.getPhone() != null) account.setPhone(request.getPhone());
        if (request.getFullName() != null) account.setFullName(request.getFullName());
        var updatedAccount = accountService.update(account);
        var response = accountMapper.toAdminSaveAccountResponse(updatedAccount);
        if (request.getRoles() != null) {
            Set<AccountRole> accountRoles = new HashSet<>();
            for (ERole role : request.getRoles()) {
                accountRoles.add(AccountRole.builder().roleId(roleService.getByERole(role).getId()).accountId(account.getId()).build());
            }
            var updatedAccountRoles = accountRoleService.saveAll(accountRoles);
            response.setRoles(updatedAccountRoles.stream().map(accountRole -> roleService.getById(accountRole.getRoleId()).getRole()).collect(Collectors.toSet()));
        }
        return response;
    }

    public void deleteAccountById(int id) {
        accountService.deleteById(id);
    }
}
