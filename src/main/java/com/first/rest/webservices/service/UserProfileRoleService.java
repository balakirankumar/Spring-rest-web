package com.first.rest.webservices.service;

import com.first.rest.webservices.domain.UserProfile;
import com.first.rest.webservices.domain.UserProfileRole;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserProfileRoleService {


    UserProfileRole save(UserProfileRole userProfileRole);

    UserProfileRole findByUserProfileIdAndId(String userProfileId, String id);

    UserProfileRole findById(String id);

    List<UserProfileRole> findByUserProfileId(String userProfileId);

    List<UserProfileRole> findByRoleId(String roleId);


    List<com.first.rest.webservices.mediatype.UserProfileRole> getMediaType(List<UserProfileRole> userProfileRoles);


}
