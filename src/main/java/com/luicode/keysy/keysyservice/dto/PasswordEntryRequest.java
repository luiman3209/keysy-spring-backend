package com.luicode.keysy.keysyservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordEntryRequest {
    @NotBlank
    private String website;

    @NotBlank
    private String username;

    @NotBlank
    private String password;


}
