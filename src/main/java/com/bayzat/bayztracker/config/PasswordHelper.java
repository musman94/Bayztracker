package com.bayzat.bayztracker.config;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

@Component
public class PasswordHelper implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return Hashing.sha1().hashString(rawPassword, Charsets.UTF_8).toString();
    }

    @Override
    public boolean matches(CharSequence encodedPassword, String rawPassword) {
        return rawPassword.equals(encode(encodedPassword));
    }

}
