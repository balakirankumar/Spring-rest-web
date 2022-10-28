package com.first.rest.webservices.exception.exceptions;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class NotFoundException extends RuntimeException{

    private final String message;
    private final String developerMessage;
    private final Throwable cause;

    public NotFoundException() {
        this(null, null, null);
    }

    public NotFoundException(String message) {
        this(message, null, null);
    }

    public NotFoundException(String message, String developerMessage) {
        this(message, developerMessage, new NotFoundException(developerMessage));
    }

    public NotFoundException(String message, String developerMessage, Throwable cause) {
        this.message = message;
        this.developerMessage = developerMessage;
        this.cause = cause;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    @Override
    public synchronized Throwable getCause() {
        return cause;
    }


}
