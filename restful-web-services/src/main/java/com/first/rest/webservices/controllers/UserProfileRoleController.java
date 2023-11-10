package com.first.rest.webservices.controllers;

import com.first.rest.webservices.exception.constants.StatusCode;
import com.first.rest.webservices.exception.exceptions.ForBiddenException;
import com.first.rest.webservices.exception.exceptions.NotFoundException;
import com.first.rest.webservices.mediatype.UserProfileRole;
import com.first.rest.webservices.service.impl.BeanInjectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping(value = ControllerMappings.USER_PROFILE_ROLES)
public class UserProfileRoleController extends BeanInjectionService {

    @RequestMapping(method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserProfileRole>> getAllUserRoles(
            @PathVariable String userProfileId) {
        return new ResponseEntity<>(userProfileRoleService.getMediaType(userProfileRoleService.findByUserProfileId(userProfileId)),HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<com.first.rest.webservices.mediatype.UserProfileRole> addRoleToUserProfile(
            @PathVariable String userProfileId,
            @RequestBody com.first.rest.webservices.mediatype.UserProfileRole userProfileRole){

        com.first.rest.webservices.domain.UserProfileRole userProfileRole1 = new com.first.rest.webservices.domain.UserProfileRole();
        userProfileRole1.setUserProfile(userProfileService.getUserProfileById(userProfileId));
        userProfileRole1.setRole(roleService.findById(userProfileRole.getRoleId()));
        userProfileRole1 = userProfileRoleService.save(userProfileRole1);


        return  new ResponseEntity<>(userProfileRoleService.getMediaType(Collections.singletonList(userProfileRole1)).get(0), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{userProfileRoleId}",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<com.first.rest.webservices.mediatype.UserProfileRole> getRoleById(
            @PathVariable String userProfileId,
            @PathVariable String userProfileRoleId){
        if(!securityService.hasRoleManager()){
            throw new ForBiddenException(StatusCode._403.getDescription(),"You not allowed to access it ");
        }
        return  new ResponseEntity<>(userProfileRoleService.getMediaType(Collections.singletonList(userProfileRoleService.findByUserProfileIdAndId(userProfileId,userProfileRoleId))).get(0), HttpStatus.CREATED);
    }
}
