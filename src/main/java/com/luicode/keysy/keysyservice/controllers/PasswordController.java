package com.luicode.keysy.keysyservice.controllers;

import com.luicode.keysy.keysyservice.dtos.CommonErrorResponse;
import com.luicode.keysy.keysyservice.dtos.PasswordEntryRequest;
import com.luicode.keysy.keysyservice.dtos.PasswordEntryResponse;
import com.luicode.keysy.keysyservice.entities.User;
import com.luicode.keysy.keysyservice.exceptions.KeysyException;
import com.luicode.keysy.keysyservice.services.PasswordService;
import com.luicode.keysy.keysyservice.utils.KeysyUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passwords")
public class PasswordController {

    private final PasswordService passwordService;
    private final KeysyUtils keysyUtils;
    @Autowired
    public PasswordController(PasswordService passwordService, KeysyUtils keysyUtils) {
        this.passwordService = passwordService;
        this.keysyUtils = keysyUtils;
    }

    @GetMapping
    public ResponseEntity<Object> getPasswords() {
        try{
            User user = keysyUtils.extractUserFromSecurityContext();
            List<PasswordEntryResponse> passwordEntries = passwordService.getAllPasswords(user.getEmail());
            return ResponseEntity.ok(passwordEntries);
        }catch (KeysyException e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getCode())).body(CommonErrorResponse.builder()
                    .message(e.getMessage())
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(500).body("Internal server error");
        }

    }

    @PostMapping
    public ResponseEntity<Object> addPassword(@Valid @RequestBody PasswordEntryRequest passwordRequest) {
        try{
            User user = keysyUtils.extractUserFromSecurityContext();
            passwordService.addPassword(passwordRequest, user.getEmail());
            return ResponseEntity.status(201).body("Password entry successfully created");
        }catch (KeysyException e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getCode())).body(CommonErrorResponse.builder()
                    .message(e.getMessage())
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(500).body("Internal server error");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPassword(@PathVariable Long id) {
        try{
            User user = keysyUtils.extractUserFromSecurityContext();
            PasswordEntryResponse passwordResponse = passwordService.getPasswordById(id, user.getEmail());
            return ResponseEntity.ok(passwordResponse);
        }catch (KeysyException e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getCode())).body(CommonErrorResponse.builder()
                    .message(e.getMessage())
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePassword(@PathVariable Long id,
                                                 @Valid @RequestBody PasswordEntryRequest passwordRequest) {
        try{
            User user = keysyUtils.extractUserFromSecurityContext();
            passwordService.updatePassword(id, passwordRequest, user.getEmail());
            return ResponseEntity.status(201).body("Password entry successfully updated");
        }catch (KeysyException e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getCode())).body(CommonErrorResponse.builder()
                    .message(e.getMessage())
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePassword(@PathVariable Long id) {
        try{
            User user = keysyUtils.extractUserFromSecurityContext();
            passwordService.deletePassword(id, user.getEmail());
            return ResponseEntity.status(201).body("Password entry successfully deleted");
        }catch (KeysyException e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getCode())).body(CommonErrorResponse.builder()
                    .message(e.getMessage())
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

}
