package com.first.rest.webservices.service.impl;

import com.first.rest.webservices.service.SecurityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class SecurityServiceImpl extends BeanInjectionService implements SecurityService {


    @Override
    public Map<String, Object> getSecurityContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities();
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", authentication.getPrincipal());
        claims.put("roles", roles);
        return claims;
    }

    public boolean hasRoleManager() {
        Map<String, Object> claims = getSecurityContext();
        List<String> roles = (List<String>) claims.get("roles");
        if (roles.contains("ROLE_MANAGER")) {
            return true;
        }
        return false;

    }
}
