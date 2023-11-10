package com.first.rest.webservices.service.impl;

import com.first.rest.webservices.config.AppLogger;
import com.first.rest.webservices.domain.Role;
import com.first.rest.webservices.domain.UserProfileRole;
import com.first.rest.webservices.service.UserProfileRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserProfileRoleServiceImpl extends BeanInjectionService implements UserProfileRoleService {

    private static final AppLogger LOGGER = AppLogger.getLogger(UserProfileServiceImpl.class);

    @Override
    public UserProfileRole findByUserProfileIdAndId(String userProfileId, String id) {
        return userProfileRoleRepository.findByUserProfileIdAndId(userProfileId, id);
    }

    @Override
    public List<UserProfileRole> findByUserProfileId(String userProfileId) {
        return userProfileRoleRepository.findByUserProfileId(userProfileId);
    }

    @Override
    public List<UserProfileRole> findByRoleId(String roleId) {
        return userProfileRoleRepository.findByRoleId(roleId);
    }

    public UserProfileRole save(UserProfileRole userProfileRole){
        userProfileRole.setId(UUID.randomUUID().toString());
        userProfileRole.setCreatedBy(userProfileRepository.findById("4bd8f699-b078-4573-8b9e-d4a5313d4e4d").get());
        userProfileRole.setCreatedDate(new Date());
        return userProfileRoleRepository.save(userProfileRole);
    }

    public UserProfileRole findById(String id){

        Optional<UserProfileRole> userProfileRole= userProfileRoleRepository.findById(id);
        if(userProfileRole.isPresent()){
            return userProfileRole.get();
        }
        return null;
    }


    @Override
    public List<com.first.rest.webservices.mediatype.UserProfileRole> getMediaType(List<UserProfileRole> userProfileRoles){
        List<com.first.rest.webservices.mediatype.UserProfileRole> userProfileRoleList = new ArrayList<>();
        userProfileRoles.forEach(userProfileRole -> {
            com.first.rest.webservices.mediatype.UserProfileRole userProfileRole1 = new com.first.rest.webservices.mediatype.UserProfileRole();
            BeanUtils.copyProperties(userProfileRole,userProfileRole1);
            userProfileRole1.setUserProfileId(userProfileRole.getUserProfile().getId());
            Role role = userProfileRole.getRole();
            userProfileRole1.setRoleId(role.getId());
            userProfileRole1.setRoleName(role.getName());
            userProfileRole1.setRoleDescription(role.getDescription());
            userProfileRole1.setCreatedBy(userProfileRole.getCreatedBy().getId());
            userProfileRole1.setCreatedDate(userProfileRole.getCreatedDate());
            userProfileRoleList.add(userProfileRole1);
        });

        return userProfileRoleList;
    }


}
