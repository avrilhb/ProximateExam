package com.example.avril.proximateexam.login.user;

import java.util.List;

/**
 * Created by avrilhb on 12/12/17.
 */

public class UserResponse {

    private Boolean success;
    private Boolean error;
    private String message;
    private List<UserData> data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserData> getData() {
        return data;
    }

    public void setData(List<UserData> data) {
        this.data = data;
    }
}
