package com.example.demoJwtSignupAuthenticationMailGeneration.Entity;

import org.springframework.http.HttpStatus;

public class Response {

    private HttpStatus status;
    private String message;
    private  Object object;

    public Response() {
    }
    public Response(HttpStatus status, String message, Object object) {
        this.status = status;
        this.message = message;
        this.object = object;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
