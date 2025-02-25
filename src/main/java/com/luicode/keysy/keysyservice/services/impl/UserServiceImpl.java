package com.luicode.keysy.keysyservice.services.impl;

import com.luicode.keysy.keysyservice.dtos.LoginRequest;
import com.luicode.keysy.keysyservice.dtos.RegisterRequest;
import com.luicode.keysy.keysyservice.entities.User;
import com.luicode.keysy.keysyservice.repositories.UserRepository;
import com.luicode.keysy.keysyservice.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(RegisterRequest input) {

        userRepository.findByEmail(input.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("User already exists");
                });

        User user = User.builder()
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .build();
        return userRepository.save(user);
    }

    public User authenticateUser(LoginRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }

}
