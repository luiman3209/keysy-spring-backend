package com.luicode.keysy.keysyservice.exceptions;

import org.springframework.context.MessageSource;

public class CustomException extends RuntimeException{
    protected MessageSource messageSource;
    protected String message;
    protected String code;

    public CustomException(MessageSource messageSource, String path, Object... objects) {
        this.messageSource = messageSource;
        this.message = getMessage(path, objects);
    }

    public CustomException(MessageSource messageSource, String path, String codePath, Object... objects) {
        this.messageSource = messageSource;
        this.message = getMessage(path, objects);
        this.code = codePath;
    }

    public String getMessage(String path, Object... objects) {
        return messageSource.getMessage(path, objects, null);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
