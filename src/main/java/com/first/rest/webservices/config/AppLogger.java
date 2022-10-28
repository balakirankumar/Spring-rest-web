package com.first.rest.webservices.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {

    private Logger LOGGER = null;
    private static AppLogger instance = null;

    private AppLogger(Class c) {
        LOGGER = LoggerFactory.getLogger(c);
    }

    public static AppLogger getLogger(Class c) {
        return new AppLogger(c);
    }

    public void warn(String message) {
        LOGGER.warn(message);
    }

    public void warn(String message, Object... objects) {
        LOGGER.warn(message, objects);
    }

    public void info(String message) {
        LOGGER.info(message);
    }

    public void info(String message, Object... objects) {
        LOGGER.info(message, objects);
    }

    public void debug(String message) {
        LOGGER.debug(message);
    }

    public void debug(String message, Object... objects) {
        LOGGER.debug(message, objects);
    }

    public void error(String message) {
        LOGGER.error(message);
    }

    public void error(String message, Object... objects) {
        LOGGER.error(message, objects);
    }
}

