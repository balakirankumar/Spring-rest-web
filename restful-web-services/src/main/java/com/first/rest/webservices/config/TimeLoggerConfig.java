package com.first.rest.webservices.config;

import com.first.rest.webservices.utils.ExceptionUtil;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
public class TimeLoggerConfig {

    private static  final  AppLogger LOGGER = AppLogger.getLogger(TimeLogger.class);

    @Before("execution(* *.*(..)) && @annotation(com.first.rest.webservices.config.TimeLogger)")
    public void before() {

        LOGGER.info("************** This is before method ***************");
    }

    @After("execution(* *.*(..)) && @annotation(com.first.rest.webservices.config.TimeLogger)")
    public void after() {

        LOGGER.info("************** This is after method ***************");
    }

//    @AfterReturning("execution(* *.*(..))")
//    public void afterReturning() {
//
//        LOGGER.info("************** This is after returning method ***************");
//    }


    @Pointcut("within(com.first.rest.webservices.custom.actuator.*)")
    public void pointCutExample(){

    }

    @After("pointCutExample()")
    public void loggingAdvice(JoinPoint joinPoint){
        LOGGER.info("In point cut example");
    }



    @Around("execution(* *.*(..)) && @annotation(com.first.rest.webservices.config.TimeLogger)")
    @Order(1)
    public Object around(ProceedingJoinPoint point){
        long startTime = System.currentTimeMillis();
        Object result;

        try {
            Object[] args = point.getArgs();
            result = point.proceed();

            LOGGER.info("INFO with args {}",args[0].toString());

        } catch (Throwable throwable) {
            LOGGER.debug(
                    String.format(
                            "Method [ %s ] in class [ %s ] threw an exception",
                            MethodSignature.class.cast(point.getSignature()).getName(),
                            MethodSignature.class.cast(point.getSignature()).getDeclaringTypeName()));
            throw ExceptionUtil.wrapCheckedException(throwable);
        }

        LOGGER.debug(
                String.format(
                        "Method [ %s ] in class [ %s ] finished in %s",
                        MethodSignature.class.cast(point.getSignature()).getName(),
                        MethodSignature.class.cast(point.getSignature()).getDeclaringTypeName(),
                        DurationFormatUtils.formatDurationHMS(System.currentTimeMillis() - startTime)));

        return result;
    }
}
