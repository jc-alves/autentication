package com.dev.autenticacao.core.util;



import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordValidator {

    /**
     *
     * @param password
     * @return valor bolleano
     */
    public boolean validate(String password) {
        Pattern pattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[a-zA-Z0-9]{8,32}$");
        Matcher matcher = pattern.matcher(password);
        password = password.trim();

        if (password == null || password.isBlank()) {
            return false;
        }

        return matcher.find();

    }
}
