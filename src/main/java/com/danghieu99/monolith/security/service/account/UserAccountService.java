package com.danghieu99.monolith.security.service.account;

import com.danghieu99.monolith.security.dto.account.request.UserChangePasswordRequest;
import com.danghieu99.monolith.security.dto.account.request.UserEditAccountDetailsRequest;
import com.danghieu99.monolith.security.dto.account.response.UserEditAccountResponse;
import com.danghieu99.monolith.security.dto.account.response.UserGetAccountDetailsResponse;
import com.danghieu99.monolith.security.dto.account.response.UserGetProfileResponse;
import com.danghieu99.monolith.security.entity.Account;
import com.danghieu99.monolith.security.mapper.AccountMapper;
import com.danghieu99.monolith.security.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final AccountCrudService accountCrudService;

    private final AccountMapper accountMapper;

    private final AuthenticationManager authenticationManager;

    private final AuthenticationService authenticationService;

    public UserGetProfileResponse getUserProfile(String uuid) {
        return accountMapper.toUserGetProfileResponse(accountCrudService.getByUUID(UUID.fromString(uuid)));
    }

    public UserGetAccountDetailsResponse getCurrentAccountDetails() {
        return accountMapper.toUserAccountDetailsResponse(accountCrudService.getById(authenticationService.getCurrentUserDetails().getId()));
    }

    public String getCurrentUserUUID() {
        return accountCrudService.getById(authenticationService.getCurrentUserDetails().getId()).getId().toString();
    }

    public UserEditAccountResponse editAccountDetails(UserEditAccountDetailsRequest request) {
        Account account = accountCrudService.getById(authenticationService.getCurrentUserDetails().getId());
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
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationService.getCurrentUserDetails().getUsername(), request.getNewPassword()));
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot authenticate with current password");
        }
        Account account = accountCrudService.getById(authenticationService.getCurrentUserDetails().getId());
        account.setPassword(request.getNewPassword());
        accountCrudService.update(account);
    }
}