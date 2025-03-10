package com.luicode.keysy.keysyservice.services.impl;

import com.luicode.keysy.keysyservice.dtos.GetAllPasswordsResponse;
import com.luicode.keysy.keysyservice.dtos.PasswordEntryRequest;
import com.luicode.keysy.keysyservice.dtos.PasswordEntryResponse;
import com.luicode.keysy.keysyservice.entities.User;
import com.luicode.keysy.keysyservice.entities.UserPassword;
import com.luicode.keysy.keysyservice.exceptions.KeysyException;
import com.luicode.keysy.keysyservice.mappers.PasswordResponseMapper;
import com.luicode.keysy.keysyservice.repositories.UserPasswordRepository;
import com.luicode.keysy.keysyservice.repositories.UserRepository;
import com.luicode.keysy.keysyservice.services.CryptoService;
import com.luicode.keysy.keysyservice.services.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final UserPasswordRepository userPasswordRepository;
    private final UserRepository userRepository;
    private final PasswordResponseMapper passwordResponseMapper;
    private final CryptoService cryptoService;

    private final MessageSource messageSource;


    private User checkUserExistenceOrThrowException(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new KeysyException(messageSource, "error.keysy.user.not.found", HttpStatus.NOT_FOUND.name()));
    }

    @Override
    public GetAllPasswordsResponse getAllPasswords(String username) {

        User user = checkUserExistenceOrThrowException(username);

        List<UserPassword> passwordEntries = userPasswordRepository.findByUserId(user.getId());

        List<PasswordEntryResponse> respList;

        try {
            respList = passwordResponseMapper.toPasswordEntryResponseList(passwordEntries);
        } catch (Exception e) {
            throw new KeysyException(messageSource, "error.generic", HttpStatus.BAD_REQUEST.name());
        }

        return GetAllPasswordsResponse.builder()
                .passwords(respList)
                .build();
    }

    @Override
    public PasswordEntryResponse addPassword(PasswordEntryRequest passwordRequest, String username) {

        User user = checkUserExistenceOrThrowException(username);


        try {

            String cryptedPassword = cryptoService.encrypt(passwordRequest.getPassword());

            return passwordResponseMapper.toPasswordEntryResponse(userPasswordRepository.save(UserPassword.builder()
                .entryName(passwordRequest.getEntryName())
                .username(passwordRequest.getUsername())
                .cryptedPassword(cryptedPassword)
                .userId(user.getId())
                .build()));

        } catch (Exception e) {
            throw new KeysyException(messageSource, "error.generic", HttpStatus.INTERNAL_SERVER_ERROR.name());
        }

        

    }

    @Override
    public PasswordEntryResponse getPasswordById(Long id, String username) {

        User user = checkUserExistenceOrThrowException(username);

        try {
            return passwordResponseMapper.toPasswordEntryResponse(userPasswordRepository.findByIdAndUserId(id, user.getId())
                    .orElseThrow(() -> new KeysyException(messageSource, "error.keysy.password.not.found", HttpStatus.NOT_FOUND.name())));
        } catch (Exception e) {
            throw new KeysyException(messageSource, "error.generic", HttpStatus.INTERNAL_SERVER_ERROR.name());
        }
    }

    @Override
    public PasswordEntryResponse updatePassword(Long id, PasswordEntryRequest passwordRequest, String username) {

        User user = checkUserExistenceOrThrowException(username);

        UserPassword pwdToUpdate =
                userPasswordRepository.findByIdAndUserId(id, user.getId())
                        .orElseThrow(() -> new KeysyException(messageSource, "error.keysy.password.not.found", HttpStatus.NOT_FOUND.name()));

        
        try {
            String cryptedPassword = cryptoService.encrypt(passwordRequest.getPassword());

            if(nonNull(passwordRequest.getEntryName())) pwdToUpdate.setEntryName(passwordRequest.getEntryName());
            if(nonNull(passwordRequest.getUsername())) pwdToUpdate.setUsername(passwordRequest.getUsername());
            if(nonNull(passwordRequest.getPassword())) pwdToUpdate.setCryptedPassword(cryptedPassword);

            return passwordResponseMapper.toPasswordEntryResponse(userPasswordRepository.save(pwdToUpdate));

        } catch (Exception e) {
            throw new KeysyException(messageSource, "error.generic", HttpStatus.INTERNAL_SERVER_ERROR.name());
        }

        
    }

    @Override
    public void deletePassword(Long id, String username) {

        User user = checkUserExistenceOrThrowException(username);

        UserPassword pwdToDelete =
                userPasswordRepository.findByIdAndUserId(id, user.getId())
                        .orElseThrow(() -> new KeysyException(messageSource, "error.keysy.password.not.found", HttpStatus.NOT_FOUND.name()));

        userPasswordRepository.delete(pwdToDelete);

    }
}