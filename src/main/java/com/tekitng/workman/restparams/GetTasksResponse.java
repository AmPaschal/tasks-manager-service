package com.tekitng.workman.restparams;

import com.tekitng.workman.models.TaskData;

import java.util.List;

/**
 * @author Amusuo Paschal C.
 * @since 11/3/2020 11:01 PM
 */

public class GetTasksResponse extends BaseResponse {

    private List<TaskData> tasks;

    public List<TaskData> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskData> tasks) {
        this.tasks = tasks;
    }
}
