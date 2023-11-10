package com.first.rest.webservices.service;

import com.first.rest.webservices.domain.Role;

import java.util.List;

public interface RoleService {

    Role findByName(String role);

    Role findById(String roleId);

    List<Role> findAll();

    Role saveRole(com.first.rest.webservices.mediatype.Role role);

    List<com.first.rest.webservices.mediatype.Role> getMediaTypeList(List<Role> roles);

    com.first.rest.webservices.mediatype.Role getMediaType(Role role);
}
