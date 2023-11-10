package com.first.rest.webservices.service.impl;


import com.first.rest.webservices.config.AppLogger;
import com.first.rest.webservices.domain.Role;
import com.first.rest.webservices.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleServiceImpl extends BeanInjectionService implements RoleService {

    public  static AppLogger LOGGER = AppLogger.getLogger(RoleServiceImpl.class);


    @Override
    public Role findByName(String name) {
        LOGGER.info("Searching Role by name [ {} ]",name);
        return  roleRepository.findByName(name);
    }

    public Role findById(String roleId){
        Optional<Role> optionalRole = roleRepository.findById(roleId);

        if(optionalRole.isPresent()){
            return roleRepository.findById(roleId).get();
        }
        return null;
    }


    @Override
    public List<Role> findAll() {
        LOGGER.info("Getting all roles");
        return  roleRepository.findAll();
    }

    @Override
    public Role saveRole(com.first.rest.webservices.mediatype.Role role) {
        LOGGER.info("Saving Role [ {} ]",role);
        Role role1 =getRoleDomainData(role);
        return roleRepository.save(role1);
    }


    public Role getRoleDomainData(com.first.rest.webservices.mediatype.Role roleMediaType){
        Role role = new Role();
        BeanUtils.copyProperties(roleMediaType,role);
        role.setId(UUID.randomUUID().toString());
        role.setCreatedBy(userProfileRepository.findById("4bd8f699-b078-4573-8b9e-d4a5313d4e4d").get());
        role.setCreatedDate(new Date());
        return role;
    }

    public List<com.first.rest.webservices.mediatype.Role> getMediaTypeList(List<Role> roles){
        List<com.first.rest.webservices.mediatype.Role> rolesMediaTypes = new ArrayList<>();

        roles.forEach(role->{
            com.first.rest.webservices.mediatype.Role roleMedia =
                    new com.first.rest.webservices.mediatype.Role();
            BeanUtils.copyProperties(role,roleMedia);
            rolesMediaTypes.add(roleMedia);
        });
        return rolesMediaTypes;
    }

    public com.first.rest.webservices.mediatype.Role getMediaType(Role role){
        com.first.rest.webservices.mediatype.Role roleMedia =
                new com.first.rest.webservices.mediatype.Role();
        BeanUtils.copyProperties(role,roleMedia);
        return roleMedia;
    }
}

