package com.danghieu99.monolith.security.controller;

import com.danghieu99.monolith.security.dto.auth.request.LoginRequest;
import com.danghieu99.monolith.security.dto.auth.request.SignupRequest;
import com.danghieu99.monolith.security.dto.auth.response.LoginResponse;
import com.danghieu99.monolith.security.dto.auth.response.LogoutResponse;
import com.danghieu99.monolith.security.dto.auth.response.SignupResponse;
import com.danghieu99.monolith.security.service.auth.AuthenticationService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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
        response.getCookies().forEach(cookie -> headers.add(HttpHeaders.SET_COOKIE, cookie.toString()));
        return ResponseEntity.ok()
                .headers(headers)
//                .header(HttpHeaders.AUTHORIZATION, jwtCookie.getValue())
//                        .header("Access-Control-Allow-Origin", "*")
//                        .header("Access-Control-Allow-Headers", "true")
//                        .header("Access-Control-Allow-Credentials", "true")
//                        .header("Access-Control-Allow-Methods", "*")
//                        .header("Access-Control-Max-Age", "1209600")
                .body(response.getBody());
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest request) {
        SignupResponse response = authenticationService.register(request);
//        HttpHeaders headers = new HttpHeaders();
//        response.getCookies().forEach(cookie -> headers.add(HttpHeaders.SET_COOKIE, cookie.toString()));
        return ResponseEntity.ok()
//                .headers(headers)
//                .header(HttpHeaders.AUTHORIZATION, jwtCookie.getValue())
//                        .header("Access-Control-Allow-Origin", "*")
//                        .header("Access-Control-Allow-Headers", "*")
//                        .header("Access-Control-Allow-Credentials", "true")
//                        .header("Access-Control-Allow-Methods", "*")
//                        .header("Access-Control-Max-Age", "1209600")
                .body(response.getBody());
    }

    @PostMapping("/user/auth/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        LogoutResponse response = authenticationService.logout(request);
        return ResponseEntity.ok()
//                .header("Access-Control-Allow-Credentials", "true")
                .body(response.getBody());
    }

    @PostMapping("user/auth/logout-all")
    public ResponseEntity<?> logoutFromAllDevices() {
        LogoutResponse response = authenticationService.logoutFromAllDevices();
        return ResponseEntity.ok()
//                .header("Access-Control-Allow-Credentials", "true")
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