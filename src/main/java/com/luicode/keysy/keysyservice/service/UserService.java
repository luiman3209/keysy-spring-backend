package com.luicode.keysy.keysyservice.service;

import com.luicode.keysy.keysyservice.dto.LoginRequest;
import com.luicode.keysy.keysyservice.dto.RegisterRequest;

public interface UserService {
    void registerUser(RegisterRequest registerRequest);
    String authenticateUser(LoginRequest loginRequest);
}
