package com.tekitng.workman.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;

import javax.enterprise.context.ApplicationScoped;

/**
 * @author Amusuo Paschal C.
 * @since 10/27/2020 2:24 PM
 */

@ApplicationScoped
public class PasswordUtils {

    public String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public boolean verifyPassword(String hashedPassword, String plainPassword) {
        BCrypt.Result verifyResult = BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword);
        return verifyResult.verified;
    }
}
