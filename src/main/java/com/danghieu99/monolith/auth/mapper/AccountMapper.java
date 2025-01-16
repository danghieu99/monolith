package com.danghieu99.monolith.auth.mapper;

import com.danghieu99.monolith.auth.dto.request.account.AdminSaveAccountRequest;
import com.danghieu99.monolith.auth.dto.request.auth.SignupRequest;
import com.danghieu99.monolith.auth.dto.response.account.UserGetAccountDetailsResponse;
import com.danghieu99.monolith.auth.dto.response.account.UserGetProfileResponse;
import com.danghieu99.monolith.auth.entity.Account;
import com.danghieu99.monolith.auth.entity.Role;
import com.danghieu99.monolith.auth.enums.ERole;
import com.danghieu99.monolith.auth.service.account.RoleService;
import org.mapstruct.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface AccountMapper {

    @Mappings({
            @Mapping(target = "roles", qualifiedByName = "ERolesToRoles"),
            @Mapping(target = "password", qualifiedByName = "encodePassword")
    })
    Account adminSaveRequestToAccount(AdminSaveAccountRequest adminSaveRequest, @Context RoleService roleService);

    @Mapping(target = "roles", qualifiedByName = "rolesToRoleNames")
    UserGetAccountDetailsResponse accountToUserAccountDetailsResponse(Account account);

    @Mapping(target = "roles", qualifiedByName = "rolesToRoleNames")
    UserGetProfileResponse accountToUserGetProfileResponse(Account account);

    @Mapping(target = "password", qualifiedByName = "encodePassword")
    Account signUpRequestToAccount(SignupRequest signupRequest);

    @Named("rolesToRoleNames")
    default Set<String> rolesToRoleNames(Set<Role> roles) {
        return roles.stream().map(role -> role.getRole().name()).collect(Collectors.toSet());
    }

    @Named("ERolesToRoles")
    default Set<Role> ERolesToRoles(Set<ERole> roleNames, @Context RoleService roleService) {
        return roleNames.stream().map(roleService::getByRole).collect(Collectors.toSet());
    }

    @Named("encodePassword")
    default String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}