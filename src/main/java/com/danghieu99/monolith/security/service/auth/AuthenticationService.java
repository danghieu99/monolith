package com.danghieu99.monolith.security.service.auth;

import com.danghieu99.monolith.security.config.auth.AuthTokenProperties;
import com.danghieu99.monolith.security.config.auth.UserDetailsImpl;
import com.danghieu99.monolith.security.dto.auth.request.LoginRequest;
import com.danghieu99.monolith.security.dto.auth.request.SignupRequest;
import com.danghieu99.monolith.security.dto.auth.response.*;
import com.danghieu99.monolith.security.entity.Account;
import com.danghieu99.monolith.security.entity.Role;
import com.danghieu99.monolith.security.entity.Token;
import com.danghieu99.monolith.security.constant.ERole;
import com.danghieu99.monolith.security.mapper.AccountMapper;
import com.danghieu99.monolith.security.service.dao.AccountService;
import com.danghieu99.monolith.security.service.dao.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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

    private final RoleService roleService;

    private final RefreshTokenService refreshTokenService;

    private final AccountMapper accountMapper;

    private final AccountService accountService;

    private final AuthTokenProperties authTokenProperties;

    private final TokenAuthenticationService tokenAuthenticationService;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public LoginResponse authenticate(LoginRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        int userId = userDetails.getId();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        ResponseCookie refreshToken = ResponseCookie.from(authTokenProperties.getRefreshTokenName(), tokenAuthenticationService.buildRefreshToken(userDetails)).build();
        String accessToken = tokenAuthenticationService.buildAccessToken(userDetails);
        var saveToken = Token.builder()
                .userId(userId)
                .tokenValue(refreshToken.getValue())
                .expiration(authTokenProperties.getRefreshTokenExpireMs())
                .build();
        refreshTokenService.save(saveToken);

        HttpHeaders headers = new HttpHeaders();
//        response.getCookies().forEach(cookie -> headers.add(HttpHeaders.SET_COOKIE, cookie.toString()));
        headers.add(HttpHeaders.AUTHORIZATION, refreshToken.getValue());
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Headers", "true");
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Allow-Methods", "*");
        headers.add("Access-Control-Max-Age", String.valueOf(authTokenProperties.getRefreshTokenExpireMs()));
        var body = LoginResponseBody.builder()
                .roles(roles)
                .username(userDetails.getUsername())
                .message("Login success!")
                .build();
        return LoginResponse.builder()
                .body(body)
                .headers(headers)
                .build();
    }

    @Transactional
    public SignupResponse register(SignupRequest request) {
        Account account = accountMapper.toAccount(request);
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        Account registeredAccount = accountService.save(account);
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roleService.getByERole(ERole.ROLE_USER));
        SignupResponseBody responseBody = SignupResponseBody.builder().username(registeredAccount.getUsername())
                .roles(accountMapper.rolesToRoleNames(userRoles)).message("Signup success!").build();
        return SignupResponse.builder().body(responseBody).build();
    }

    @Transactional
    public LogoutResponse logout(HttpServletRequest request) {
        tokenAuthenticationService.deleteCurrentRefreshToken(request);
        LogoutResponseBody response = LogoutResponseBody.builder().message("Logout success!").build();
        return LogoutResponse.builder().body(response).build();
    }

    @Transactional
    public LogoutResponse logoutFromAllDevices() {
        refreshTokenService.deleteByUserId(getCurrentUserDetails().getId());
        LogoutResponseBody response = LogoutResponseBody.builder().message("Logout from all devices success!").build();
        return LogoutResponse.builder().body(response).build();
    }

    public ResponseCookie refreshAuthentication() {
        return ResponseCookie.from(authTokenProperties.getRefreshTokenName(), tokenAuthenticationService.buildRefreshToken(getCurrentUserDetails())).build();
    }

    public UserDetailsImpl getCurrentUserDetails() {
        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}