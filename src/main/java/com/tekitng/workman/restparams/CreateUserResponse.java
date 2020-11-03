package com.tekitng.workman.restparams;

import com.tekitng.workman.models.UserData;

/**
 * @author Amusuo Paschal C.
 * @since 10/27/2020 11:55 AM
 */

public class CreateUserResponse extends BaseResponse {

    private UserData data;

    public CreateUserResponse() {
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }
}
