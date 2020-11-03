package com.tekitng.workman.restparams;

import com.tekitng.workman.models.UserData;

/**
 * @author Amusuo Paschal C.
 * @since 10/28/2020 10:42 AM
 */

public class ValidateTokenResponse extends BaseResponse {

    private UserData data;

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }
}
