package com.tekitng.workman.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tekitng.workman.entities.User;
import com.tekitng.workman.models.UserData;
import com.tekitng.workman.repositories.UserRepository;
import com.tekitng.workman.restparams.CreateUserRequest;
import com.tekitng.workman.restparams.CreateUserResponse;
import com.tekitng.workman.restparams.GetUsersResponse;
import com.tekitng.workman.restparams.LoginRequest;
import com.tekitng.workman.restparams.LoginResponse;
import com.tekitng.workman.restparams.ValidateTokenResponse;
import com.tekitng.workman.utils.DtoManager;
import com.tekitng.workman.utils.PasswordUtils;
import com.tekitng.workman.utils.ResponseUtils;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Amusuo Paschal C.
 * @since 10/27/2020 1:03 AM
 */

@ApplicationScoped
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private DtoManager dtoManager;

    @Inject
    private PasswordUtils passwordUtils;

    @Inject
    private ResponseUtils responseUtils;

    @Inject
    JsonWebToken jwt;

    private static final Logger log = Logger.getLogger(UserService.class);

    public Response createNewUser(CreateUserRequest request) {

        CreateUserResponse createUserResponse = new CreateUserResponse();

        // Check if user already exist
        User userByEmail = userRepository.findUserByEmail(request.getEmail().toLowerCase());

        if (userByEmail != null) {
            return responseUtils.updateResponseData(createUserResponse, false, "User already exists");
        }

        // Create User entity
        User user = createUserEntityFromRequest(request);

        // Save User entity
        userRepository.saveUser(user);

        createUserResponse.setData(dtoManager.toUserData(user));

        return responseUtils.updateResponseData(createUserResponse, true, "User saved successfully");
    }


    public Response login(LoginRequest request) {
        LoginResponse loginResponse = new LoginResponse();

        // Check if user already exist
        User userByEmail = userRepository.findUserByEmail(request.getEmail().toLowerCase());

        if (userByEmail == null) {
            return responseUtils.updateResponseData(loginResponse, false, "User with email does not exist");
        }

        boolean passwordMatch = passwordUtils.verifyPassword(userByEmail.getPassword(), request.getPassword());

        if (!passwordMatch) {
            return responseUtils.updateResponseData(loginResponse, false, "Password does not match");
        }

        UserData userData = dtoManager.toUserData(userByEmail);

        loginResponse.setUser(userData);

        String token = generateUserToken(userData);

        loginResponse.setData(token);

        return responseUtils.updateResponseData(loginResponse, true, "Login Successful");
    }

    private String generateUserToken(UserData userData) {

        Set<String> roles = new HashSet<>();
        roles.add("User");

        if (userData.isAdmin()) {
            roles.add("Admin");
        }

        return Jwt.claims().claim(Claims.email.name(), userData.getEmail())
                .claim("firstName", userData.getFirstName())
                .claim("lastName", userData.getLastName())
                .claim("userData", getUserDataString(userData))
                .groups(roles)
                .upn(userData.getEmail())
                .sign();
    }

    private String getUserDataString(UserData userData) {

        try {
            return new ObjectMapper().writeValueAsString(userData);
        } catch (JsonProcessingException e) {
            log.error("An error occured while transforming to json", e);
            return null;
        }
    }

    private User createUserEntityFromRequest(CreateUserRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail().toLowerCase()
        );
        user.setPassword(passwordUtils.hashPassword(request.getPassword()));
        user.setCreateDate(new Date());
        user.setAdmin(request.isAdmin());
        return user;
    }

    public Response getAllUsers() {
        List<User> allUsers = userRepository.findAllUsers();

        List<UserData> userDataList = allUsers.stream().map(user -> dtoManager.toUserData(user)).collect(Collectors.toList());

        GetUsersResponse response = new GetUsersResponse();
        response.setUsers(userDataList);

        return responseUtils.updateResponseData(response, true, "Users retrieved successfully");

    }

    public Response validateToken() {
        ValidateTokenResponse response = new ValidateTokenResponse();

        if (jwt == null || jwt.getClaim("userData") == null) {
            return responseUtils.updateResponseData(response, false, "The token is invalid");
        }

        String userData = jwt.getClaim("userData").toString();

        UserData user = null;
        try {
            user = new ObjectMapper().readValue(userData, UserData.class);
        } catch (JsonProcessingException e) {
            log.error("An error occurred while reading json string", e);
        }

        response.setData(user);

        return responseUtils.updateResponseData(response, true, "Success");
    }
}
