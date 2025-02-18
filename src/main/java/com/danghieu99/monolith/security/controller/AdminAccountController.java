package com.danghieu99.monolith.security.controller;

import com.danghieu99.monolith.security.dto.account.request.AdminSaveAccountRequest;
import com.danghieu99.monolith.security.service.account.AdminAccountService;
import com.danghieu99.monolith.security.service.dao.AccountDaoService;
import jakarta.validation.Valid;
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
    private final AccountDaoService accountDaoService;

    @GetMapping("")
    public ResponseEntity<?> getAccounts(@RequestParam(required = false) Pageable pageable) {
        return ResponseEntity.ok(accountDaoService.getAll(pageable));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable int id) {
        return ResponseEntity.ok(accountDaoService.getById(id));
    }

    @GetMapping("/username")
    public ResponseEntity<?> getAccountByUsername(@RequestParam String username) {
        return ResponseEntity.ok(accountDaoService.getByUsername(username));
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