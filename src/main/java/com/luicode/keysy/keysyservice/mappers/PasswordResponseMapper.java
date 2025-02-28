package com.luicode.keysy.keysyservice.mappers;

import com.luicode.keysy.keysyservice.dtos.PasswordEntryResponse;
import com.luicode.keysy.keysyservice.entities.UserPassword;
import com.luicode.keysy.keysyservice.services.CryptoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PasswordResponseMapper {

    @Autowired
    private CryptoService cryptoService;

    public List<PasswordEntryResponse> toPasswordEntryResponseList (List<UserPassword> userPasswordList) throws Exception {
        
        List<PasswordEntryResponse> respList = new ArrayList<>();

        for(UserPassword userPass : userPasswordList){
            respList.add(toPasswordEntryResponse(userPass));
        }

        return respList;
    }

    public PasswordEntryResponse toPasswordEntryResponse (UserPassword userPassword) throws Exception {
        return PasswordEntryResponse.builder()
                .id(userPassword.getId())
                .entryName(userPassword.getEntryName())
                .username(userPassword.getUsername())
                .password(cryptoService.decrypt(userPassword.getCryptedPassword()))
                .build();
    }
}
