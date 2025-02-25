package com.luicode.keysy.keysyservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordEntryRequest {

    @NotBlank
    private String entryName;

    @NotBlank
    private String username;

    @NotBlank
    private String password;


}
