package com.luicode.keysy.keysyservice.utils;

import com.luicode.keysy.keysyservice.entities.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class KeysyUtils {

    public User extractUserFromSecurityContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
    }
}
