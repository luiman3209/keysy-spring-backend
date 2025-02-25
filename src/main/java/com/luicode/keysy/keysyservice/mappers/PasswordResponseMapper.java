package com.luicode.keysy.keysyservice.mappers;

import com.luicode.keysy.keysyservice.dtos.PasswordEntryResponse;
import com.luicode.keysy.keysyservice.entities.UserPassword;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PasswordResponseMapper {

    public List<PasswordEntryResponse> toPasswordEntryResponseList (List<UserPassword> userPasswordList) {
        return userPasswordList.stream()
                .map(this::toPasswordEntryResponse)
                .toList();
    }

    public PasswordEntryResponse toPasswordEntryResponse (UserPassword userPassword) {
        return PasswordEntryResponse.builder()
                .id(userPassword.getId())
                .entryName(userPassword.getEntryName())
                .username(userPassword.getUsername())
                .password(userPassword.getCryptedPassword())
                .build();
    }
}
