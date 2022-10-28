package com.first.rest.webservices.exception;


import com.fasterxml.jackson.annotation.JsonInclude;

public class ErrorMessage {

    private String status;
    private int code;
    private String message;
    private String developerMessage;

    public ErrorMessage() {
    }

    public ErrorMessage(String status, int code, String message, String developerMessage) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.developerMessage = developerMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

}
