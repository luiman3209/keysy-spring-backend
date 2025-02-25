package com.luicode.keysy.keysyservice.services;

import com.luicode.keysy.keysyservice.dtos.PasswordEntryRequest;
import com.luicode.keysy.keysyservice.dtos.PasswordEntryResponse;

import java.util.List;

public interface PasswordService {
    List<PasswordEntryResponse> getAllPasswords(String username);
    void addPassword(PasswordEntryRequest passwordRequest, String username);
    PasswordEntryResponse getPasswordById(Long id, String username);
    void updatePassword(Long id, PasswordEntryRequest passwordRequest, String username);
    void deletePassword(Long id, String username);
}
