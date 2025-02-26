package com.luicode.keysy.keysyservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordEntryRequest {

    @NotBlank
    private String entryName;

    @NotBlank
    private String username;

    @NotBlank
    private String password;


}
