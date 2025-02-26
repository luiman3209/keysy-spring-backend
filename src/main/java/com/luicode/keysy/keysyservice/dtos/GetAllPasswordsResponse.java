package com.luicode.keysy.keysyservice.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetAllPasswordsResponse {
    List<PasswordEntryResponse> passwords;
}
