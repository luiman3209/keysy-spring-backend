package com.luicode.keysy.keysyservice.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonErrorResponse {

    private String message;
}
