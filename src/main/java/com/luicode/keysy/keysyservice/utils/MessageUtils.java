package com.luicode.keysy.keysyservice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageUtils {
    @Autowired
    private MessageSource messageSource;

    public String getMessage(String path){ return messageSource.getMessage(path, new Object[] {}, null); }
    public String getMessage(String path, Object ... objects){ return messageSource.getMessage(path,objects, null); }

}
