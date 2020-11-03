package com.tekitng.workman.restparams;

import com.tekitng.workman.models.UserData;

/**
 * @author Amusuo Paschal C.
 * @since 10/27/2020 2:33 PM
 */

public class LoginResponse extends BaseResponse {

    private UserData user;
    private String data;

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
