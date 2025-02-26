package com.luicode.keysy.keysyservice.controllers;

import com.luicode.keysy.keysyservice.dtos.CommonResponse;
import com.luicode.keysy.keysyservice.dtos.PasswordEntryRequest;
import com.luicode.keysy.keysyservice.entities.User;
import com.luicode.keysy.keysyservice.exceptions.KeysyException;
import com.luicode.keysy.keysyservice.services.PasswordService;
import com.luicode.keysy.keysyservice.utils.KeysyUtils;
import com.luicode.keysy.keysyservice.utils.MessageUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passwords")
public class PasswordController {

    private final PasswordService passwordService;
    private final KeysyUtils keysyUtils;

    private final MessageUtils messageUtils;
    @Autowired
    public PasswordController(PasswordService passwordService,
                              KeysyUtils keysyUtils,
                              MessageUtils messageUtils) {
        this.passwordService = passwordService;
        this.keysyUtils = keysyUtils;
        this.messageUtils = messageUtils;
    }

    @GetMapping
    public ResponseEntity<Object> getPasswords() {
        try{
            User user = keysyUtils.extractUserFromSecurityContext();
            return ResponseEntity.ok(passwordService.getAllPasswords(user.getEmail()));
        }catch (KeysyException e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getCode())).body(CommonResponse.builder()
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
            return ResponseEntity.status(200).body(CommonResponse.builder()
                            .message(messageUtils.getMessage("password.added.successfully"))
                            .build());
        }catch (KeysyException e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getCode())).body(CommonResponse.builder()
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
            return ResponseEntity.ok(passwordService.getPasswordById(id, user.getEmail()));

        }catch (KeysyException e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getCode())).body(CommonResponse.builder()
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
            return ResponseEntity.status(200).body(CommonResponse.builder()
                    .message(messageUtils.getMessage("password.updated.successfully")).build());
        }catch (KeysyException e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getCode())).body(CommonResponse.builder()
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
            return ResponseEntity.status(200).body(CommonResponse.builder().message(messageUtils.getMessage("password.deleted.successfully"))
                    .build());
        }catch (KeysyException e){
            return ResponseEntity.status(HttpStatus.valueOf(e.getCode())).body(CommonResponse.builder()
                    .message(e.getMessage())
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

}
