package com.tekitng.workman.restparams;

import javax.validation.constraints.NotBlank;

/**
 * @author Amusuo Paschal C.
 * @since 10/29/2020 6:13 AM
 */

public class CreateTaskRequest {

    @NotBlank (message = "title cannot be blank")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
