package com.danghieu99.monolith.security.service.account;

import com.danghieu99.monolith.security.dto.account.request.AdminSaveAccountRequest;
import com.danghieu99.monolith.security.dto.account.response.AdminSaveAccountResponse;
import com.danghieu99.monolith.security.entity.Account;
import com.danghieu99.monolith.security.entity.AccountRole;
import com.danghieu99.monolith.security.mapper.AccountMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminAccountService {

    private final AccountCrudService accountCrudService;
    private final AccountMapper accountMapper;
    private final RoleCrudService roleCrudService;
    private final AccountRoleCrudService accountRoleCrudService;

    public Page<Account> getAccounts(Pageable pageable) {
        return pageable == null ? accountCrudService.getFirstPage() : accountCrudService.getAllPaged(pageable);
    }

    public Account getAccountById(int id) {
        return accountCrudService.getById(id);
    }

    public Account getAccountByUsername(String username) {
        return accountCrudService.getByUsername(username);
    }

    public Page<Account> searchAccountsByUsername(String username, Pageable pageable) {
        return accountCrudService.searchByUsernameContains(username, pageable);
    }

    @Transactional
    public AdminSaveAccountResponse addAccount(AdminSaveAccountRequest request) {
        var newAccount = accountCrudService.save(accountMapper.toAccount(request));
        Set<AccountRole> accountRoles = new HashSet<>();
        for (var role : request.getRoles()) {
            accountRoles.add(AccountRole.builder().roleId(roleCrudService.getByERole(role).getId()).accountId(newAccount.getId()).build());
        }
        accountRoleCrudService.saveAll(accountRoles);
        return accountMapper.toAdminSaveAccountResponse(accountCrudService.save(newAccount));
    }

    @Transactional
    public AdminSaveAccountResponse updateAccount(int id, AdminSaveAccountRequest request) {
        Account account = accountCrudService.getById(id);
        if (request.getUsername() != null) account.setUsername(request.getUsername());
        if (request.getPassword() != null) account.setPassword(request.getPassword());
        if (request.getEmail() != null) account.setEmail(request.getEmail());
        if (request.getGender() != null) account.setGender(request.getGender());
        if (request.getPhone() != null) account.setPhone(request.getPhone());
        if (request.getFullName() != null) account.setFullName(request.getFullName());
        var updatedAccount = accountCrudService.update(account);
        var response = accountMapper.toAdminSaveAccountResponse(updatedAccount);
        if (request.getRoles() != null) {
            Set<AccountRole> accountRoles = new HashSet<>();
            for (var role : request.getRoles()) {
                accountRoles.add(AccountRole.builder().roleId(roleCrudService.getByERole(role).getId()).accountId(account.getId()).build());
            }
            var updatedRoles = accountRoleCrudService.saveAll(accountRoles);
            response.setRoles(updatedRoles.stream().map(accountRole -> roleCrudService.getById(accountRole.getRoleId()).getRole()).collect(Collectors.toSet()));
        }
        return response;
    }

    public void deleteAccountById(int id) {
        accountCrudService.deleteById(id);
    }
}
