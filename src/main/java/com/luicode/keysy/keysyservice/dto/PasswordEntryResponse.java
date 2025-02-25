package com.luicode.keysy.keysyservice.dto;

import lombok.Data;

@Data
public class PasswordEntryResponse {
    private Long id;
    private String website;
    private String username;
    private String password;


}
