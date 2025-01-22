package com.danghieu99.monolith.auth.controller.rest;

import com.danghieu99.monolith.auth.service.account.AdminAccountService;
import jakarta.validation.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.danghieu99.monolith.auth.dto.request.account.AdminSaveAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/accounts")
@RequiredArgsConstructor
@Validated
public class AdminAccountController {

    private final AdminAccountService aAccountService;

    @GetMapping("")
    public ResponseEntity<?> getAccounts(@RequestParam(required = false) Pageable pageable) {
        return ResponseEntity.ok(aAccountService.getAccounts(pageable));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable int id) {
        return ResponseEntity.ok(aAccountService.getAccountById(id));
    }

    @GetMapping("/username")
    public ResponseEntity<?> getAccountByUsername(@RequestParam String username) {
        return ResponseEntity.ok(aAccountService.getAccountByUsername(username));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchAccountsByUsername(@Size(min = 3, max = 20) @Pattern(regexp = "^[a-zA-Z0-9-_]+$") @RequestParam String username,
                                                      @RequestParam(required = false) Pageable pageable) {
        return ResponseEntity.ok(aAccountService.searchAccountsByUsername(username, pageable));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAccount(@Valid @RequestBody AdminSaveAccountRequest request) {
        return ResponseEntity.ok(aAccountService.addAccount(request));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateAccount(@RequestParam int id, @Valid @RequestBody AdminSaveAccountRequest request) {
        return ResponseEntity.ok(aAccountService.updateAccount(id, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount(@RequestParam int id) {
        aAccountService.deleteAccountById(id);
        return ResponseEntity.ok("Delete success!");
    }
}