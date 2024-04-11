package com.first.rest.webservices.config;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ExceptionAspect {
    private static final AppLogger LOGGER = AppLogger.getLogger(ExceptionAspect.class);

    @AfterThrowing(pointcut = "execution(* *(..))",throwing = "runtimeException")
    public void processException(RuntimeException runtimeException) throws Throwable{
        LOGGER.error(runtimeException.getMessage());
    }
}
