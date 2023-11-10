package com.first.rest.webservices.exception;

import com.first.rest.webservices.exception.constants.StatusCode;
import com.first.rest.webservices.exception.exceptions.BadRequestException;
import com.first.rest.webservices.exception.exceptions.ForBiddenException;
import com.first.rest.webservices.exception.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionMapper {


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorMessage notFoundException(NotFoundException e,HttpServletResponse response) {
//        LOGGER.debug(e.getDeveloperMessage(), e.getCause());
        ErrorMessage msg = new ErrorMessage();
        msg.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
        msg.setCode(HttpStatus.NOT_FOUND.value());
        msg.setMessage(e.getMessage());
        msg.setDeveloperMessage(e.getDeveloperMessage());
        return msg;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage processValidationError(MethodArgumentNotValidException ex,
                                               HttpServletResponse response) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
//        LOGGER.debug("Validation error on field {}, exception: ", errors, ex);
        ErrorMessage msg = new ErrorMessage();
        msg.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        msg.setCode(HttpStatus.BAD_REQUEST.value());
        msg.setMessage(StatusCode._400.getDescription());
        msg.setDeveloperMessage("Request caused an MethodArgumentNotValidException: " + errors.toString());;
        return msg;
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorMessage internalServerError(Exception e, HttpServletResponse response) {
//        LOGGER.debug("Request caused a lock acquire exception", e.getCause());
        ErrorMessage msg = new ErrorMessage();
        msg.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        msg.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        msg.setMessage(StatusCode._500.getDescription());
        msg.setDeveloperMessage("Internal Server Error:" + e.getMessage());
        return msg;
    }

    @ExceptionHandler(ForBiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorMessage forBiddenError(ForBiddenException e, HttpServletResponse response) {
//        LOGGER.debug(e.getDeveloperMessage(), e.getCause());
        ErrorMessage msg = new ErrorMessage();
        msg.setStatus(HttpStatus.FORBIDDEN.getReasonPhrase());
        msg.setCode(HttpStatus.FORBIDDEN.value());
        msg.setMessage(StatusCode._403.getDescription());
        msg.setDeveloperMessage(e.getDeveloperMessage());
        return msg;
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorMessage userNotFound(UsernameNotFoundException e, HttpServletResponse response) {
//        LOGGER.debug(e.getDeveloperMessage(), e.getCause());
        ErrorMessage msg = new ErrorMessage();
        msg.setStatus(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        msg.setCode(HttpStatus.UNAUTHORIZED.value());
        msg.setMessage(StatusCode._401.getDescription());
        msg.setDeveloperMessage(e.getMessage());
        return msg;
    }


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage badRequestException(BadRequestException e, HttpServletResponse response) {
//        LOGGER.debug(e.getDeveloperMessage(), e.getCause());
        ErrorMessage msg = new ErrorMessage();
        msg.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
        msg.setCode(HttpStatus.BAD_REQUEST.value());
        msg.setMessage(e.getMessage());
        msg.setDeveloperMessage(e.getDeveloperMessage());
        return msg;
    }
}
