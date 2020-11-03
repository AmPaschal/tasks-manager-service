package com.tekitng.workman.restparams;

import com.tekitng.workman.models.UserData;

import java.util.List;

/**
 * @author Amusuo Paschal C.
 * @since 11/4/2020 12:24 AM
 */

public class GetUsersResponse extends BaseResponse {

    private List<UserData> users;

    public List<UserData> getUsers() {
        return users;
    }

    public void setUsers(List<UserData> users) {
        this.users = users;
    }
}
