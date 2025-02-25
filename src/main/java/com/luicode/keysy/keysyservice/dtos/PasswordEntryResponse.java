package com.luicode.keysy.keysyservice.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordEntryResponse {
    private Long id;
    private String entryName;
    private String username;
    private String password;

}
