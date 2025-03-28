package com.danghieu99.monolith.security.service.account;

import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.security.dto.account.request.UserChangePasswordRequest;
import com.danghieu99.monolith.security.dto.account.request.UserEditAccountDetailsRequest;
import com.danghieu99.monolith.security.dto.account.response.UserEditAccountResponse;
import com.danghieu99.monolith.security.dto.account.response.UserGetAccountDetailsResponse;
import com.danghieu99.monolith.security.entity.Account;
import com.danghieu99.monolith.security.mapper.AccountMapper;
import com.danghieu99.monolith.security.repository.jpa.AccountRepository;
import com.danghieu99.monolith.security.repository.jpa.RoleRepository;
import com.danghieu99.monolith.security.service.auth.AuthenticationService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;
    private final RoleRepository roleRepository;

    public UserGetAccountDetailsResponse getPrivateAccountDetailsByUUID(@NotEmpty final String uuid) {
        var detailsResponse = accountMapper.toUserAccountDetailsResponse(accountRepository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> new ResourceNotFoundException("Account", "uuid", uuid)));
        detailsResponse.setRoles(accountMapper.rolesToRoleNames(roleRepository.findByAccountUUID(UUID.fromString(uuid))));
        return detailsResponse;
    }

    public UserEditAccountResponse editAccountDetails(@NotNull final UserEditAccountDetailsRequest request) {
        Account account = accountRepository.findById(authenticationService.getCurrentUserDetails().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", authenticationService.getCurrentUserDetails().getId()));
        if (request.getUsername() != null) account.setUsername(request.getUsername());
        if (request.getEmail() != null) account.setEmail(request.getEmail());
        if (request.getGender() != null) account.setGender(request.getGender());
        if (request.getPhone() != null) account.setPhone(request.getPhone());
        if (request.getFullName() != null) account.setFullName(request.getFullName());
        accountRepository.save(account);
        return UserEditAccountResponse.builder().message("Edit success!").build();
    }

    //add email confirmation
    public void changeUserAccountPassword(@NotNull final UserChangePasswordRequest request) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationService.getCurrentUserDetails().getUsername(), request.getNewPassword()));
        } catch (Exception e) {
            throw new AuthenticationException("Cannot authenticate!") {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }
        Account account = accountRepository.findById(authenticationService.getCurrentUserDetails().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", authenticationService.getCurrentUserDetails().getId()));
        if (request.getNewPassword().equals(account.getPassword())) {
            throw new IllegalArgumentException("Password not changed!");
        }
        account.setPassword(request.getNewPassword());
        accountRepository.save(account);
    }
}