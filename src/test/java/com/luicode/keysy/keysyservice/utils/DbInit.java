package com.luicode.keysy.keysyservice.utils;

import com.luicode.keysy.keysyservice.entities.User;
import com.luicode.keysy.keysyservice.entities.UserPassword;
import com.luicode.keysy.keysyservice.repositories.UserPasswordRepository;
import com.luicode.keysy.keysyservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DbInit {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserPasswordRepository userPasswordRepository;


    public static final String TEST_USER_EMAIL = "test@example.com";

    public void initData() {

        User user = userRepository.save(User.builder()
                        .email(TEST_USER_EMAIL)
                        .password("test")
                .build());

        userPasswordRepository.save(UserPassword.builder()
                        .userId(user.getId())
                        .entryName("Facebook")
                        .username("pippo")
                        .cryptedPassword(passwordEncoder.encode("password"))
                .build());

    }
}
