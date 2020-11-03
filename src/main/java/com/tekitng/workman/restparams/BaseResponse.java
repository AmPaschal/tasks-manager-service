package com.tekitng.workman.restparams;

import java.io.Serializable;

/**
 * @author Amusuo Paschal C.
 * @since 10/27/2020 11:54 AM
 */

public class BaseResponse implements Serializable {

    private boolean status;
    private String message;
    private String error;

    public BaseResponse() {
        this.status = false;
        this.error = "An error occured";
    }

    public void setErrorResponse(String errorMessage) {
        this.status = false;
        this.error = errorMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.status = true;
        this.error = null;
        this.message = successMessage;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
