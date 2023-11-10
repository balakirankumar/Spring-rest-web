package com.first.rest.webservices.service;

import org.springframework.security.core.Authentication;

import java.util.Map;

public interface SecurityService {

    Map<String,Object> getSecurityContext();

    boolean hasRoleManager();


}
