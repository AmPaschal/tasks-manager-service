package com.tekitng.workman.services;

import com.tekitng.workman.entities.Task;
import com.tekitng.workman.entities.User;
import com.tekitng.workman.models.TaskData;
import com.tekitng.workman.repositories.TaskRepository;
import com.tekitng.workman.repositories.UserRepository;
import com.tekitng.workman.restparams.BaseResponse;
import com.tekitng.workman.restparams.CreateTaskRequest;
import com.tekitng.workman.restparams.CreateTaskResponse;
import com.tekitng.workman.restparams.GetTasksResponse;
import com.tekitng.workman.restparams.UpdateTaskRequest;
import com.tekitng.workman.utils.DtoManager;
import com.tekitng.workman.utils.ResponseUtils;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Amusuo Paschal C.
 * @since 10/29/2020 6:12 AM
 */

@RequestScoped
public class TaskService {

    @Inject
    JsonWebToken jwt;

    @Inject
    TaskRepository taskRepository;

    @Inject
    UserRepository userRepository;


    @Inject
    ResponseUtils responseUtils;

    @Inject
    DtoManager dtoManager;

    private static final Logger log = Logger.getLogger(TaskService.class);


    public Response createTask(CreateTaskRequest request) {

        CreateTaskResponse response = new CreateTaskResponse();

        String userEmail = jwt.getClaim(Claims.email.name());

        if (userEmail == null) {
            return responseUtils.updateResponseData(response, false, "User email not found in token");
        }

        User user = userRepository.findUserByEmail(userEmail);
        if (user == null) {
            return responseUtils.updateResponseData(response, true, "User not found");
        }

        // Create Task Entity
        Task task = createTaskEntity(request, user);

        // Save Task Entity
        taskRepository.saveTask(task);

        //Return created task object
        response.setTask(dtoManager.toTaskData(task));

        return responseUtils.updateResponseData(response, true, "Task Saved Successfully");
    }

    private Task createTaskEntity(CreateTaskRequest request, User user) {

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setOwner(user);
        task.setCompleted(false);
        task.setDateCreated(new Date());
        task.setDateLastUpdated(new Date());

        return task;
    }

    public Response updateTask(UpdateTaskRequest request, Long taskId) {

        CreateTaskResponse response = new CreateTaskResponse();

        // Fetch task by id
        Task task = taskRepository.getTaskById(taskId);

        if (task == null) {
            return responseUtils.updateResponseData(response, false, "Task not found");
        }

        // Confirm task owner is equal to user updating task
        String ownerEmail = jwt.getClaim(Claims.email.name());

        log.info("Owner email is " + ownerEmail);

        if (ownerEmail == null) {
            return responseUtils.updateResponseData(response, false, "User email not found in token");
        }

        if (task.getOwner() != null && !task.getOwner().getEmail().equals(ownerEmail)) {
            return responseUtils.updateResponseData(response, false, "You are not allowed to carry out this operation");
        }

        // Update task properties
        Task updatedTask = getUpdatedTask(task, request);
        taskRepository.updateTask(updatedTask);

        // Return updated task in response
        response.setTask(dtoManager.toTaskData(updatedTask));
        return responseUtils.updateResponseData(response, true, "Task updated successfully");
    }

    private Task getUpdatedTask(Task task, UpdateTaskRequest request) {
        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }

        if (request.isCompleted() != null) {
            task.setCompleted(request.isCompleted());
        }

        task.setDateLastUpdated(new Date());

        return task;
    }

    public Response getTasks(Long taskId) {

        if (taskId != null) {
            return getSingleTask(taskId);

        }

        GetTasksResponse response = new GetTasksResponse();

        String userEmail = jwt.getClaim(Claims.email.name());

        if (userEmail == null) {
            return responseUtils.updateResponseData(response, false, "User email not found in token");
        }

        User user = userRepository.findUserByEmail(userEmail);
        if (user == null) {
            return responseUtils.updateResponseData(response, true, "User not found");
        }

        List<TaskData> taskDataList = user.getTasks().stream().map(task -> dtoManager.toTaskData(task)).collect(Collectors.toList());

        response.setTasks(taskDataList);

        return responseUtils.updateResponseData(response, true, "Tasks retrieved successfully");


    }

    private Response getSingleTask(Long taskId) {
        GetTasksResponse response = new GetTasksResponse();

        // Fetch task by id
        Task task = taskRepository.getTaskById(taskId);

        if (task == null) {
            return responseUtils.updateResponseData(response, false, "Task not found");
        }

        // Confirm task owner is equal to user updating task
        String ownerEmail = jwt.getClaim(Claims.email.name());

        if (ownerEmail == null) {
            return responseUtils.updateResponseData(response, false, "User email not found in token");
        }

        if (task.getOwner() != null && !task.getOwner().getEmail().equals(ownerEmail)) {
            return responseUtils.updateResponseData(response, false, "You are not allowed to carry out this operation");
        }

        List<TaskData> taskDataList = new ArrayList<>();
        taskDataList.add(dtoManager.toTaskData(task));

        response.setTasks(taskDataList);

        return responseUtils.updateResponseData(response, true, "Task retrieved successfully");
    }

    public Response deleteTask(Long taskId) {
        BaseResponse response = new BaseResponse();

        // Fetch task by id
        Task task = taskRepository.getTaskById(taskId);

        if (task == null) {
            return responseUtils.updateResponseData(response, false, "Task not found");
        }

        // Confirm task owner is equal to user updating task
        String ownerEmail = jwt.getClaim(Claims.email.name());

        if (ownerEmail == null) {
            return responseUtils.updateResponseData(response, false, "User email not found in token");
        }

        if (task.getOwner() != null && !task.getOwner().getEmail().equals(ownerEmail)) {
            return responseUtils.updateResponseData(response, false, "You are not allowed to carry out this operation");
        }

        taskRepository.deleteTask(task);

        return responseUtils.updateResponseData(response, true, "Task deleted successfully");

    }
}
