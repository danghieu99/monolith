package com.danghieu99.monolith.security.controller;

import com.danghieu99.monolith.security.config.auth.UserDetailsImpl;
import com.danghieu99.monolith.security.dto.auth.request.LoginRequest;
import com.danghieu99.monolith.security.dto.auth.request.SignupRequest;
import com.danghieu99.monolith.security.dto.auth.response.LoginResponse;
import com.danghieu99.monolith.security.dto.auth.response.LogoutResponse;
import com.danghieu99.monolith.security.dto.auth.response.SignupResponse;
import com.danghieu99.monolith.security.service.auth.AuthenticationService;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/auth/login")
    @JsonFormat
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authenticationService.authenticate(request);
        HttpHeaders headers = new HttpHeaders();
        return ResponseEntity.ok()
                .headers(headers)
                .body(response.getBody());
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest request) {
        SignupResponse response = authenticationService.register(request);
        return ResponseEntity.ok()
                .body(response.getBody());
    }

    @PostMapping("/user/auth/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        LogoutResponse response = authenticationService.logout(request);
        return ResponseEntity.ok()
                .body(response.getBody());
    }

    @PostMapping("user/auth/logout-all")
    public ResponseEntity<?> logoutFromAllDevices(@AuthenticationPrincipal @NotNull UserDetailsImpl userDetails) {
        LogoutResponse response = authenticationService.logoutFromAllDevices(userDetails);
        return ResponseEntity.ok()
                .body(response.getBody());
    }

    @GetMapping("user/auth/refresh")
    public ResponseEntity<?> getNewRefreshToken() {
        ResponseCookie cookie = authenticationService.refreshAuthentication();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok()
                .headers(headers)
                .body("Refresh success!");
    }
}