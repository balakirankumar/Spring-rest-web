package com.first.rest.webservices.service.impl;

import com.first.rest.webservices.domain.UserProfile;
import com.first.rest.webservices.service.UserProfileService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserProfileServiceImpl extends BeanInjectionService implements UserProfileService {


    @Override
    public List<UserProfile> getUserProfiles() {
        List<UserProfile> userProfiles = (List<UserProfile>) userProfileRepository.findAll();
        return userProfiles;
    }

    @Override
    public UserProfile createUserProfile(UserProfile userProfile){
        return userProfileRepository.save(userProfile);
    }

    @Override
    public UserProfile getUserProfileById(String id){
        Optional<UserProfile> optional = userProfileRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    public void deleteUserProfile(String id){
        userProfileRepository.deleteById(id);
    }

}
