package com.first.rest.webservices.exception.exceptions;


public class BadRequestException extends RuntimeException {

    private final String message;
    private final String developerMessage;
    private final Throwable cause;

    public BadRequestException() {
        this(null, null, null);
    }

    public BadRequestException(String message) {
        this(message, null, null);
    }

    public BadRequestException(String message, String developerMessage) {
        this(message, developerMessage, new BadRequestException(developerMessage));
    }

    public BadRequestException(String message, String developerMessage, Throwable cause) {
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

