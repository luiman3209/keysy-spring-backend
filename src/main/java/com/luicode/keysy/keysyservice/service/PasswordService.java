package com.luicode.keysy.keysyservice.service;

import com.luicode.keysy.keysyservice.dto.PasswordEntryRequest;
import com.luicode.keysy.keysyservice.dto.PasswordEntryResponse;

import java.util.List;

public interface PasswordService {
    List<PasswordEntryResponse> getAllPasswords(String username);
    void addPassword(PasswordEntryRequest passwordRequest, String username);
    PasswordEntryResponse getPasswordById(Long id, String username);
    boolean updatePassword(Long id, PasswordEntryRequest passwordRequest, String username);
    boolean deletePassword(Long id, String username);
}
