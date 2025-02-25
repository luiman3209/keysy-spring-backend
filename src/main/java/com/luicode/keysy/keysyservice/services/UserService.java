package com.luicode.keysy.keysyservice.services;

import com.luicode.keysy.keysyservice.dtos.LoginRequest;
import com.luicode.keysy.keysyservice.dtos.RegisterRequest;
import com.luicode.keysy.keysyservice.entities.User;


public interface UserService {
    User registerUser(RegisterRequest registerRequest);
    User authenticateUser(LoginRequest loginRequest);
}
