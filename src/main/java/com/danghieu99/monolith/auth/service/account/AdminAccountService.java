package com.danghieu99.monolith.auth.service.account;

import com.danghieu99.monolith.auth.dto.request.account.AdminSaveAccountRequest;
import com.danghieu99.monolith.auth.entity.Account;
import com.danghieu99.monolith.auth.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminAccountService {

    private final AccountCrudService accountCrudService;
    private final AccountMapper accountMapper;
    private final RoleCrudService roleCrudService;

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

    public Account addAccount(AdminSaveAccountRequest request) {
        return accountCrudService.create(accountMapper.adminSaveRequestToAccount(request, roleCrudService));
    }

    public Account updateAccount(int id, AdminSaveAccountRequest request) {
        Account account = accountCrudService.getById(id);
        if (request.getUsername() != null) account.setUsername(request.getUsername());
        if (request.getPassword() != null) account.setPassword(request.getPassword());
        if (request.getRoles() != null) {
            account.setRoles(request.getRoles().stream().map(roleCrudService::getByERole).collect(Collectors.toSet()));
        }
        if (request.getEmail() != null) account.setEmail(request.getEmail());
        if (request.getGender() != null) account.setGender(request.getGender());
        if (request.getPhone() != null) account.setPhone(request.getPhone());
        if (request.getFullName() != null) account.setFullName(request.getFullName());
        return accountCrudService.update(account);
    }

    public void deleteAccountById(int id) {
        accountCrudService.deleteById(id);
    }
}
