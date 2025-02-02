package com.danghieu99.monolith.auth.mapper;

import com.danghieu99.monolith.auth.dto.request.account.AdminSaveAccountRequest;
import com.danghieu99.monolith.auth.dto.request.auth.SignupRequest;
import com.danghieu99.monolith.auth.dto.response.account.UserGetAccountDetailsResponse;
import com.danghieu99.monolith.auth.dto.response.account.UserGetProfileResponse;
import com.danghieu99.monolith.auth.entity.Account;
import com.danghieu99.monolith.auth.entity.Role;
import com.danghieu99.monolith.auth.enums.ERole;
import com.danghieu99.monolith.auth.service.account.RoleCrudService;
import org.mapstruct.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AccountMapper {

    @Mappings({
            @Mapping(target = "roles", ignore = true),
            @Mapping(target = "password", ignore = true),
    })
    Account toAccount(AdminSaveAccountRequest adminSaveRequest);

    @Mapping(target = "roles", qualifiedByName = "rolesToRoleNames")
    UserGetAccountDetailsResponse toUserAccountDetailsResponse(Account account);

    @Mapping(target = "roles", qualifiedByName = "rolesToRoleNames")
    UserGetProfileResponse toUserGetProfileResponse(Account account);

    @Mapping(target = "password", ignore = true)
    Account toAccount(SignupRequest signupRequest);

    @Named("rolesToRoleNames")
    default Set<String> rolesToRoleNames(Set<Role> roles) {
        return roles.stream().map(role -> role.getRole().name()).collect(Collectors.toSet());
    }
}