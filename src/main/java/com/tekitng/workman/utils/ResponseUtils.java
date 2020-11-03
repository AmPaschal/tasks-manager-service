package com.tekitng.workman.utils;

import com.tekitng.workman.restparams.BaseResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

/**
 * @author Amusuo Paschal C.
 * @since 10/28/2020 12:32 AM
 */

@ApplicationScoped
public class ResponseUtils {

    public Response updateResponseData(BaseResponse response, boolean status, String message) {

        if (status) {
            response.setSuccessMessage(message);
        } else {
            response.setErrorResponse(message);
        }

        return Response.ok(response).build();
    }
}
