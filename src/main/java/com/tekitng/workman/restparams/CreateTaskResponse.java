package com.tekitng.workman.restparams;

import com.tekitng.workman.models.TaskData;

/**
 * @author Amusuo Paschal C.
 * @since 11/3/2020 10:32 PM
 */

public class CreateTaskResponse extends BaseResponse {


    private TaskData task;

    public TaskData getTask() {
        return task;
    }

    public void setTask(TaskData task) {
        this.task = task;
    }
}
