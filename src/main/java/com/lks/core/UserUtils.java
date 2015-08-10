package com.lks.core;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by lokkur on 8/10/2015.
 */
public class UserUtils {

    public static String createHashedPassword(String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = bCryptPasswordEncoder.encode(password);
        return hashedPassword;
    }
}
