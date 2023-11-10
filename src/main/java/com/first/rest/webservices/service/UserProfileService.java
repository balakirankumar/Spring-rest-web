package com.first.rest.webservices.service;

import com.first.rest.webservices.domain.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserProfileService  {

    Page<UserProfile> getUserProfiles(Pageable pageable);

    UserProfile createUserProfile(UserProfile userProfile);

    UserProfile getUserProfileById(String id);

    void deleteUserProfile(String id);

    UserProfile findByEmail(String email);

}
