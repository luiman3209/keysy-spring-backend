package com.luicode.keysy.keysyservice.service.impl;

import com.luicode.keysy.keysyservice.dto.LoginRequest;
import com.luicode.keysy.keysyservice.dto.RegisterRequest;
import com.luicode.keysy.keysyservice.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Override
    public void registerUser(RegisterRequest registerRequest) {
        // Register logic (e.g., save user to the database)
    }

    @Override
    public String authenticateUser(LoginRequest loginRequest) {
        return null;
    }
}
