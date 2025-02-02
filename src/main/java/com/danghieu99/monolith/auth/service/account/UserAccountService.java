package com.danghieu99.monolith.auth.service.account;

import com.danghieu99.monolith.auth.config.authentication.UserDetailsImpl;
import com.danghieu99.monolith.auth.dto.request.account.UserChangePasswordRequest;
import com.danghieu99.monolith.auth.dto.request.account.UserEditAccountDetailsRequest;
import com.danghieu99.monolith.auth.dto.response.account.UserEditAccountResponse;
import com.danghieu99.monolith.auth.dto.response.account.UserGetAccountDetailsResponse;
import com.danghieu99.monolith.auth.dto.response.account.UserGetProfileResponse;
import com.danghieu99.monolith.auth.entity.Account;
import com.danghieu99.monolith.auth.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final AccountCrudService accountCrudService;

    private final AccountMapper accountMapper;

    private final AuthenticationManager authenticationManager;


    public UserDetailsImpl getCurrentUserDetails() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public UserGetProfileResponse getUserProfile(String uuid) {
        return accountMapper.toUserGetProfileResponse(accountCrudService.getByUUID(UUID.fromString(uuid)));
    }

    public UserGetAccountDetailsResponse getCurrentAccountDetails() {
        return accountMapper.toUserAccountDetailsResponse(accountCrudService.getById(getCurrentUserDetails().getId()));
    }

    public String getCurrentUserUUID() {
        return accountCrudService.getById(getCurrentUserDetails().getId()).getId().toString();
    }

    public UserEditAccountResponse editAccountDetails(UserEditAccountDetailsRequest request) {
        Account account = accountCrudService.getById(getCurrentUserDetails().getId());
        if (request.getUsername() != null) account.setUsername(request.getUsername());
        if (request.getEmail() != null) account.setEmail(request.getEmail());
        if (request.getGender() != null) account.setGender(request.getGender());
        if (request.getPhone() != null) account.setPhone(request.getPhone());
        if (request.getFullName() != null) account.setFullName(request.getFullName());
        accountCrudService.update(account);
        return UserEditAccountResponse.builder().message("Edit success!").build();
    }

    //add email confirm
    public void changeUserAccountPassword(UserChangePasswordRequest request) {
        if (request.getNewPassword().equals(request.getOldPassword())) {
            throw new IllegalArgumentException("Password not changed");
        }
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(getCurrentUserDetails().getUsername(), request.getNewPassword()));
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot authenticate with current password");
        }
        Account account = accountCrudService.getById(getCurrentUserDetails().getId());
        account.setPassword(request.getNewPassword());
        accountCrudService.update(account);
    }
}