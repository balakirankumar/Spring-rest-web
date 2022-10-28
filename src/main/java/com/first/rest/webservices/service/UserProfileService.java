package com.first.rest.webservices.service;

import com.first.rest.webservices.domain.UserProfile;

import java.util.List;

public interface UserProfileService  {

    List<UserProfile> getUserProfiles();

    UserProfile createUserProfile(UserProfile userProfile);

    UserProfile getUserProfileById(String id);

    void deleteUserProfile(String id);

}
