package com.tekitng.workman.resources;

import com.tekitng.workman.restparams.BaseResponse;
import com.tekitng.workman.restparams.CreateTaskRequest;
import com.tekitng.workman.restparams.UpdateTaskRequest;
import com.tekitng.workman.services.TaskService;
import com.tekitng.workman.utils.ResponseUtils;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Amusuo Paschal C.
 * @since 10/28/2020 2:23 PM
 */

@Path("/tasks")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TaskResource {

    @Inject
    TaskService taskService;

    @Inject
    ResponseUtils responseUtils;

    @POST
    @RolesAllowed("User")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public Response createTask(@Valid CreateTaskRequest request) {

        try {
            return taskService.createTask(request);
        } catch (Exception ex) {
            return responseUtils.updateResponseData(new BaseResponse(), false, ex.getLocalizedMessage());
        }



    }

    @PUT
    @Path("/{taskId}")
    @RolesAllowed("User")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public Response updateTask(@Valid UpdateTaskRequest request, @PathParam("taskId") @Valid @NotNull Long id) {
        try {
            return taskService.updateTask(request, id);
        } catch (Exception ex) {
            return responseUtils.updateResponseData(new BaseResponse(), false, ex.getLocalizedMessage());
        }

    }

    @GET
    @RolesAllowed("User")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public Response getTasks(@QueryParam("id") Long id) {
        try {
            return taskService.getTasks(id);
        } catch (Exception ex) {
            return responseUtils.updateResponseData(new BaseResponse(), false, ex.getLocalizedMessage());
        }

    }

    @DELETE
    @Path("/{taskId}")
    @RolesAllowed("User")
    @SecurityRequirement(name = "bearerAuth")
    @Transactional
    public Response deleteTask(@PathParam("taskId") @Valid @NotNull Long id) {
        try {
            return taskService.deleteTask(id);
        } catch (Exception ex) {
            return responseUtils.updateResponseData(new BaseResponse(), false, ex.getLocalizedMessage());
        }

    }

}
