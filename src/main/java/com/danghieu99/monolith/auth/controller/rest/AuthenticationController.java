package com.danghieu99.monolith.auth.controller.rest;

import com.danghieu99.monolith.auth.dto.request.auth.LoginRequest;
import com.danghieu99.monolith.auth.dto.request.auth.SignupRequest;
import com.danghieu99.monolith.auth.dto.response.auth.LoginResponse;
import com.danghieu99.monolith.auth.dto.response.auth.LogoutResponse;
import com.danghieu99.monolith.auth.dto.response.auth.SignupResponse;
import com.danghieu99.monolith.auth.service.auth.AuthenticationService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
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

    @PostMapping("/register")
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

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        LogoutResponse response = authenticationService.logout(request);
        return ResponseEntity.ok()
//                .header("Access-Control-Allow-Credentials", "true")
                .body(response.getBody());
    }

    @PostMapping("/logout/all")
    public ResponseEntity<?> logoutAllDevices() {
        LogoutResponse response = authenticationService.logoutFromAllDevices();
        return ResponseEntity.ok()
//                .header("Access-Control-Allow-Credentials", "true")
                .body(response.getBody());
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshAuthentication() {
        ResponseCookie cookie = authenticationService.refreshAuthentication();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok()
                .headers(headers)
                .body("Refresh success!");
    }
}