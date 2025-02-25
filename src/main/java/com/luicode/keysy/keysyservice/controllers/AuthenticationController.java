package com.luicode.keysy.keysyservice.controllers;

import com.luicode.keysy.keysyservice.dtos.LoginRequest;
import com.luicode.keysy.keysyservice.dtos.LoginResponse;
import com.luicode.keysy.keysyservice.dtos.RegisterRequest;
import com.luicode.keysy.keysyservice.entities.User;
import com.luicode.keysy.keysyservice.services.JwtService;
import com.luicode.keysy.keysyservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;

    private final UserService userService;



    public AuthenticationController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;

    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerUserDto) {

        try{
            User registeredUser = userService.registerUser(registerUserDto);

            return ResponseEntity.ok(registeredUser);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginUserDto) {
        User authenticatedUser = userService.authenticateUser(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
            .token(jwtToken)
            .expiresIn(jwtService.getExpirationTime()).build();

        return ResponseEntity.ok(loginResponse);
    }
}