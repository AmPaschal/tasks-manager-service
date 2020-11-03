package com.tekitng.workman.restparams;

import java.io.Serializable;

/**
 * @author Amusuo Paschal C.
 * @since 10/27/2020 2:32 PM
 */

public class LoginRequest implements Serializable {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
