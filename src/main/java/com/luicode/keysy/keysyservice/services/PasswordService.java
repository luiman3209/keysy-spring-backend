package com.luicode.keysy.keysyservice.services;

import com.luicode.keysy.keysyservice.dtos.GetAllPasswordsResponse;
import com.luicode.keysy.keysyservice.dtos.PasswordEntryRequest;
import com.luicode.keysy.keysyservice.dtos.PasswordEntryResponse;

public interface PasswordService {
    GetAllPasswordsResponse getAllPasswords(String username);
    PasswordEntryResponse addPassword(PasswordEntryRequest passwordRequest, String username);
    PasswordEntryResponse getPasswordById(Long id, String username);
    PasswordEntryResponse updatePassword(Long id, PasswordEntryRequest passwordRequest, String username);
    void deletePassword(Long id, String username);
}
