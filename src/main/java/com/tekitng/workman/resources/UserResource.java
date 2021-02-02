package com.tekitng.workman.resources;

import com.tekitng.workman.restparams.CreateUserRequest;
import com.tekitng.workman.restparams.LoginRequest;
import com.tekitng.workman.services.UserService;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Amusuo Paschal C.
 * @since 10/27/2020 1:03 AM
 */
@Path("/users")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserService userService;


    @POST
    @Path("/create")
    @Transactional
    public Response createNewUser(CreateUserRequest request) {
        return userService.createNewUser(request);
    }

    @POST
    @Path("/login")
    @Transactional
    public Response login(LoginRequest request) {
        return userService.login(request);
    }

    @GET
    @SecurityRequirement(name = "bearerAuth")
    @RolesAllowed("Admin")
    public Response getAllUsers() {
        return userService.getAllUsers();
    }

    @GET
    @Path("/auth")
    public Response validateToken() {
        return userService.validateToken();
    }


}
