package com.danghieu99.monolith.security.service.auth;

import com.danghieu99.monolith.security.config.auth.TokenProperties;
import com.danghieu99.monolith.security.config.auth.UserDetailsImpl;
import com.danghieu99.monolith.security.dto.auth.request.LoginRequest;
import com.danghieu99.monolith.security.dto.auth.request.SignupRequest;
import com.danghieu99.monolith.security.dto.auth.response.*;
import com.danghieu99.monolith.security.entity.Account;
import com.danghieu99.monolith.security.entity.Role;
import com.danghieu99.monolith.security.entity.Token;
import com.danghieu99.monolith.security.constant.ERole;
import com.danghieu99.monolith.security.mapper.AccountMapper;
import com.danghieu99.monolith.security.service.account.AccountCrudService;
import com.danghieu99.monolith.security.service.account.RoleCrudService;
import com.danghieu99.monolith.security.service.token.RefreshTokenCrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final RoleCrudService roleCrudService;

    private final RefreshTokenCrudService refreshTokenCrudService;

    private final AccountMapper accountMapper;

    private final AccountCrudService accountCrudService;

    private final TokenProperties tokenProperties;

    private final TokenAuthenticationService tokenAuthenticationService;

    private final PasswordEncoder passwordEncoder;

    public LoginResponse authenticate(LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        int userId = userDetails.getId();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        List<ResponseCookie> cookies = new ArrayList<>();
        ResponseCookie accessToken = ResponseCookie.from(tokenProperties.getAccessTokenName(), tokenAuthenticationService.buildAccessToken(userDetails)).build();
        ResponseCookie refreshToken = ResponseCookie.from(tokenProperties.getRefreshTokenName(), tokenAuthenticationService.buildRefreshToken(userDetails)).build();
        cookies.add(accessToken);
        cookies.add(refreshToken);
        var saveToken = Token.builder()
                .userId(userId)
                .tokenValue(refreshToken.getValue())
                .expiration(tokenProperties.getRefreshTokenExpireMs())
                .build();
        refreshTokenCrudService.save(saveToken);
        var body = LoginResponseBody.builder()
                .roles(roles)
                .username(userDetails.getUsername())
                .message("Login success!")
                .build();
        return LoginResponse.builder().body(body).cookies(cookies).build();
    }

    public SignupResponse register(SignupRequest request) {
        Account account = accountMapper.toAccount(request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleCrudService.getByERole(ERole.ROLE_USER));
        account.setRoles(userRoles);
        Account registeredAccount = accountCrudService.create(account);
        SignupResponseBody responseBody = SignupResponseBody.builder().username(registeredAccount.getUsername())
                .roles(registeredAccount.getRoles().stream().map(role -> role.getRole().toString()).collect(Collectors.toSet())).message("Signup success!").build();
        return SignupResponse.builder().body(responseBody).build();
    }

    public LogoutResponse logout(HttpServletRequest request) {
        tokenAuthenticationService.deleteCurrentRefreshToken(request);
        LogoutResponseBody response = LogoutResponseBody.builder().message("Logout success!").build();
        return LogoutResponse.builder().body(response).build();
    }

    public LogoutResponse logoutFromAllDevices() {
        refreshTokenCrudService.deleteByUserId(getCurrentUserDetails().getId());
        LogoutResponseBody response = LogoutResponseBody.builder().message("Logout from all devices success!").build();
        return LogoutResponse.builder().body(response).build();
    }

    public ResponseCookie refreshAuthentication() {
        return ResponseCookie.from(tokenProperties.getRefreshTokenName(), tokenAuthenticationService.buildRefreshToken(getCurrentUserDetails())).build();
    }

    public UserDetailsImpl getCurrentUserDetails() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}