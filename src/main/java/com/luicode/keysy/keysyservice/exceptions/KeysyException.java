package com.luicode.keysy.keysyservice.exceptions;

import org.springframework.context.MessageSource;

public class KeysyException extends CustomException{


    public KeysyException(MessageSource messageSource, String path, Object... objects) {
        super(messageSource, path, objects);
    }

    public KeysyException(MessageSource messageSource, String path, String code, Object... objects) {
        super(messageSource, path, code, objects);
    }

}
