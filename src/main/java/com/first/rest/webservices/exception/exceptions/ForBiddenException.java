package com.first.rest.webservices.exception.exceptions;

public class ForBiddenException extends RuntimeException{

    private final String message;
    private final String developerMessage;
    private final Throwable cause;

    public ForBiddenException() {
        this(null, null, null);
    }

    public ForBiddenException(String message) {
        this(message, null, null);
    }

    public ForBiddenException(String message, String developerMessage) {
        this(message, developerMessage, new ForBiddenException(developerMessage));
    }

    public ForBiddenException(String message, String developerMessage, Throwable cause) {
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
