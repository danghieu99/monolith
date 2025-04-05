package com.danghieu99.monolith.security.controller;

import com.danghieu99.monolith.security.config.auth.UserDetailsImpl;
import com.danghieu99.monolith.security.dto.auth.request.LoginRequest;
import com.danghieu99.monolith.security.dto.auth.request.SignupRequest;
import com.danghieu99.monolith.security.service.auth.AuthenticationService;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @JsonFormat
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest request) {
        return authenticationService.register(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return authenticationService.logout(request);
    }

    @PostMapping("/logout-all")
    public ResponseEntity<?> logoutFromAllDevices(@NotNull @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return authenticationService.logoutFromAllDevices(userDetails);
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshAuthentication(@NotNull @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return authenticationService.refreshAuthentication(userDetails);
    }
}