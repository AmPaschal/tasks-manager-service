package com.tekitng.workman.utils;

import com.tekitng.workman.entities.Task;
import com.tekitng.workman.entities.User;
import com.tekitng.workman.models.TaskData;
import com.tekitng.workman.models.UserData;

import javax.enterprise.context.ApplicationScoped;
import java.util.Date;

/**
 * @author Amusuo Paschal C.
 * @since 10/27/2020 11:57 AM
 */

@ApplicationScoped
public class DtoManager {

    public UserData toUserData(User user) {
        UserData userData = new UserData();
        userData.setEmail(user.getEmail());
        userData.setPhoneNumber(user.getPhoneNumber());
        userData.setFirstName(user.getFirstName());
        userData.setLastName(user.getLastName());
        userData.setVerified(user.isVerified());
        userData.setAdmin(user.isAdmin());

        return userData;
    }

    public TaskData toTaskData(Task task) {
        TaskData taskData = new TaskData();
        taskData.setId(task.getId());
        taskData.setTitle(task.getTitle());
        taskData.setCompleted(task.isCompleted());
        taskData.setDateCreated(task.getDateCreated());
        taskData.setDateLastUpdated(task.getDateLastUpdated());
        if (task.getOwner() != null) {
            taskData.setOwner(task.getOwner().getEmail());
        }


        return taskData;
    }

}
