package com.tekitng.workman.restparams;

/**
 * @author Amusuo Paschal C.
 * @since 11/3/2020 10:45 PM
 */

public class UpdateTaskRequest {

    private String title;
    private Boolean completed;

    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
