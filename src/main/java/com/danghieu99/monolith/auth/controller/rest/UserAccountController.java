package com.danghieu99.monolith.auth.controller.rest;

import com.danghieu99.monolith.auth.dto.request.account.UserChangePasswordRequest;
import com.danghieu99.monolith.auth.dto.request.account.UserEditAccountDetailsRequest;
import com.danghieu99.monolith.auth.service.account.UserAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/account")
public class UserAccountController {

    private final UserAccountService uAccountService;

    public UserAccountController(UserAccountService uAccountService) {
        this.uAccountService = uAccountService;
    }

    @GetMapping("/my-account")
    public ResponseEntity<?> getCurrentAccountDetails() {
        return ResponseEntity.ok(uAccountService.getCurrentAccountDetails());
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestParam(name = "id") String uuid) {
        return ResponseEntity.ok(uAccountService.getUserProfile(uuid));
    }

    @GetMapping("")
    public ResponseEntity<?> getCurrentProfileUUID() {
        return ResponseEntity.ok(uAccountService.getCurrentUserUUID());
    }

    @PatchMapping("/edit")
    public ResponseEntity<?> editAccountDetails(@Valid @RequestBody UserEditAccountDetailsRequest request) {
        return ResponseEntity.ok(uAccountService.editAccountDetails(request));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody UserChangePasswordRequest request) {
        uAccountService.changeUserAccountPassword(request);
        return ResponseEntity.ok("Password change success!");
    }
}