package com.first.rest.webservices.service.impl;

import com.first.rest.webservices.config.AppLogger;
import com.first.rest.webservices.domain.UserProfile;
import com.first.rest.webservices.domain.UserProfileRole;
import com.first.rest.webservices.service.UserProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserProfileServiceImpl extends BeanInjectionService implements UserProfileService, UserDetailsService {


    public static AppLogger LOGGER = AppLogger.getLogger(UserProfileServiceImpl.class);

    private final PasswordEncoder passwordEncoder;

    public UserProfileServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<UserProfile> getUserProfiles(Pageable pageable) {

        LOGGER.info("Finding all users with pageable object [ {} ]",pageable);
        Page<UserProfile> userProfiles = userProfileRepository.findAll(pageable);
        return userProfiles;
    }

    @Override
    public UserProfile createUserProfile(UserProfile userProfile){
        LOGGER.info("Saving a user [ {} ]",userProfile);
        userProfile.setPassword(passwordEncoder.encode(userProfile.getPassword()));
        return userProfileRepository.save(userProfile);
    }

    @Override
    public UserProfile getUserProfileById(String id){
        LOGGER.info("Find  a user with id [ {} ]",id);
        Optional<UserProfile> optional = userProfileRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    public void deleteUserProfile(String id){
        userProfileRepository.deleteById(id);
    }


    public UserProfile findByEmail(String email){
        return userProfileRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfile userProfile = userProfileRepository.findByEmail(username);

        if(Objects.isNull(userProfile)){
            LOGGER.error("User with email [ {} ] is not found",username);
            throw  new UsernameNotFoundException("User with email is not found");
        }
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        List<UserProfileRole> userProfileRoles = userProfileRoleService.findByUserProfileId(userProfile.getId());
        userProfileRoles.forEach(userProfileRole -> {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(userProfileRole.getRole().getName()));
        });
        return new User(userProfile.getId(),userProfile.getPassword(),simpleGrantedAuthorities);
    }

}
