package com.danghieu99.monolith.security.controller;

import com.danghieu99.monolith.security.dto.account.request.UserChangePasswordRequest;
import com.danghieu99.monolith.security.dto.account.request.UserEditAccountDetailsRequest;
import com.danghieu99.monolith.security.service.account.UserAccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Validated
public class AccountController {

    private final UserAccountService uAccountService;

    public AccountController(UserAccountService uAccountService) {
        this.uAccountService = uAccountService;
    }

    @GetMapping("user/account")
    public ResponseEntity<?> getCurrentAccountDetails() {
        return ResponseEntity.ok(uAccountService.getCurrentAccountDetails());
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestParam(name = "id") String uuid) {
        return ResponseEntity.ok(uAccountService.getUserProfile(uuid));
    }

    @GetMapping("/user/profile")
    public ResponseEntity<?> getCurrentProfileUUID() {
        return ResponseEntity.ok(uAccountService.getUserProfile(uAccountService.getCurrentUserUUID()));
    }

    @PatchMapping("user/edit")
    public ResponseEntity<?> editAccountDetails(@Valid @RequestBody UserEditAccountDetailsRequest request) {
        return ResponseEntity.ok(uAccountService.editAccountDetails(request));
    }

    @PostMapping("user/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody UserChangePasswordRequest request) {
        uAccountService.changeUserAccountPassword(request);
        return ResponseEntity.ok("Password change success!");
    }
}