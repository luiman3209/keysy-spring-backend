package com.luicode.keysy.keysyservice.services.impl;

import com.luicode.keysy.keysyservice.dtos.PasswordEntryRequest;
import com.luicode.keysy.keysyservice.dtos.PasswordEntryResponse;
import com.luicode.keysy.keysyservice.entities.User;
import com.luicode.keysy.keysyservice.entities.UserPassword;
import com.luicode.keysy.keysyservice.exceptions.KeysyException;
import com.luicode.keysy.keysyservice.mappers.PasswordResponseMapper;
import com.luicode.keysy.keysyservice.repositories.UserPasswordRepository;
import com.luicode.keysy.keysyservice.repositories.UserRepository;
import com.luicode.keysy.keysyservice.services.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final UserPasswordRepository userPasswordRepository;
    private final UserRepository userRepository;
    private final PasswordResponseMapper passwordResponseMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    private final MessageSource messageSource;


    private User checkUserExistenceOrThrowException(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new KeysyException(messageSource, "error.incentive.triplet.not.found", HttpStatus.NOT_FOUND.name()));
    }

    @Override
    public List<PasswordEntryResponse> getAllPasswords(String username) {

        User user = checkUserExistenceOrThrowException(username);

        List<UserPassword> passwordEntries = userPasswordRepository.findByUserId(user.getId());

        return passwordResponseMapper.toPasswordEntryResponseList(passwordEntries);
    }

    @Override
    public void addPassword(PasswordEntryRequest passwordRequest, String username) {

        User user = checkUserExistenceOrThrowException(username);

        userPasswordRepository.save(UserPassword.builder()
                .entryName(passwordRequest.getEntryName())
                .username(passwordRequest.getUsername())
                .cryptedPassword(passwordEncoder.encode(passwordRequest.getPassword()))
                .userId(user.getId())
                .build());

    }

    @Override
    public PasswordEntryResponse getPasswordById(Long id, String username) {

        User user = checkUserExistenceOrThrowException(username);

        return passwordResponseMapper.toPasswordEntryResponse(userPasswordRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Password not found")));
    }

    @Override
    public void updatePassword(Long id, PasswordEntryRequest passwordRequest, String username) {

        User user = checkUserExistenceOrThrowException(username);

        UserPassword pwdToUpdate =
                userPasswordRepository.findByIdAndUserId(id, user.getId()).orElseThrow(() -> new RuntimeException("Password not found"));

        pwdToUpdate.setEntryName(passwordRequest.getEntryName());
        pwdToUpdate.setUsername(passwordRequest.getUsername());
        pwdToUpdate.setCryptedPassword(passwordEncoder.encode(passwordRequest.getPassword()));

        userPasswordRepository.save(pwdToUpdate);
    }

    @Override
    public void deletePassword(Long id, String username) {

        User user = checkUserExistenceOrThrowException(username);

        UserPassword pwdToDelete =
                userPasswordRepository.findByIdAndUserId(id, user.getId()).orElseThrow(() -> new RuntimeException("Password not found"));

        userPasswordRepository.delete(pwdToDelete);

    }
}