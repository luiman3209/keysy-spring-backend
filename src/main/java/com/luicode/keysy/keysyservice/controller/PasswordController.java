package com.luicode.keysy.keysyservice.controller;

import com.luicode.keysy.keysyservice.dto.PasswordEntryRequest;
import com.luicode.keysy.keysyservice.dto.PasswordEntryResponse;
import com.luicode.keysy.keysyservice.service.PasswordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passwords")
public class PasswordController {

    private final PasswordService passwordService;

    @Autowired
    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @GetMapping
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<PasswordEntryResponse>> getPasswords(@RequestHeader("Authorization") String token) {
        String username = "extractedUsername";
        List<PasswordEntryResponse> passwordEntries = passwordService.getAllPasswords(username);
        return ResponseEntity.ok(passwordEntries);
    }

    @PostMapping
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> addPassword(@Valid @RequestBody PasswordEntryRequest passwordRequest,
                                              @RequestHeader("Authorization") String token) {
        String username = "extractedUsername";
        passwordService.addPassword(passwordRequest, username);
        return ResponseEntity.status(201).body("Password entry successfully created");
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<PasswordEntryResponse> getPassword(@PathVariable Long id,
                                                             @RequestHeader("Authorization") String token) {
        String username = "extractedUsername";
        PasswordEntryResponse passwordResponse = passwordService.getPasswordById(id, username);
        return passwordResponse != null ? ResponseEntity.ok(passwordResponse) : ResponseEntity.status(404).build();
    }

    @PutMapping("/{id}")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> updatePassword(@PathVariable Long id,
                                                 @Valid @RequestBody PasswordEntryRequest passwordRequest,
                                                 @RequestHeader("Authorization") String token) {
        String username = "extractedUsername";
        boolean updated = passwordService.updatePassword(id, passwordRequest, username);
        return updated ? ResponseEntity.ok("Password entry updated successfully") : ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deletePassword(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String username = "extractedUsername";
        boolean deleted = passwordService.deletePassword(id, username);
        return deleted ? ResponseEntity.status(204).build() : ResponseEntity.status(404).build();
    }
}