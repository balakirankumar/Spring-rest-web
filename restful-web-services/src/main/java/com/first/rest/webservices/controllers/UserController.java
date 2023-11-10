package com.first.rest.webservices.controllers;



import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.first.rest.webservices.config.AppLogger;
import com.first.rest.webservices.exception.constants.StatusCode;
import com.first.rest.webservices.exception.exceptions.NotFoundException;
import com.first.rest.webservices.mediatype.UserProfile;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.LinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = ControllerMappings.USERS)
public class UserController extends BaseController{

    private static final AppLogger LOGGER = AppLogger.getLogger(UserController.class);

    public static List<com.first.rest.webservices.domain.UserProfile> userProfilesDomain
            =new ArrayList<>();

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> createUser(@Valid @RequestBody UserProfile userProfile){
        com.first.rest.webservices.domain.UserProfile userProfileDomain = populateUserProfileEntity(userProfile);
        LOGGER.info("User profile has been created [ {} ]",userProfileDomain);
        userProfilesDomain.add(userProfileDomain);
        userProfile = getUserProfileMediaType(userProfileDomain);
        return new ResponseEntity<>(userProfile, HttpStatus.CREATED);

    }


    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserProfile>> getUsers(){
        List<UserProfile> userProfileList = new ArrayList<>();
        userProfilesDomain.forEach(userProfile -> {
            userProfileList.add(getUserProfileMediaType(userProfile));
        });
        return new ResponseEntity<>(userProfileList, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/export", produces = "text/csv")
    public void getUsersExport(HttpServletRequest request, HttpServletResponse response) throws IOException {


        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = { "Metric", "App name", "Subscripton name", "Entitlement", "Used"};
        String[] nameMapping = { "id", "email", "firstName", "lastname", "birthDate"};
        List<UserProfile> userProfileList = new ArrayList<>();
        userProfilesDomain.forEach(userProfile -> {
            userProfileList.add(getUserProfileMediaType(userProfile));
        });

        csvWriter.writeHeader(csvHeader);
        for (UserProfile tpm : userProfileList) {
            csvWriter.write(tpm, nameMapping);
        }
        response.setContentType("text/csv");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users" + ".csv";
        response.setHeader("file-name","users.csv");
        response.setHeader(headerKey, headerValue);
        csvWriter.close();

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<UserProfile>> getUser(@PathVariable String id){
        com.first.rest.webservices.domain.UserProfile userProfileDomain = getUserById(id);
        if(userProfileDomain!=null){
//            return getResponse(userProfileDomain,HttpStatus.OK);
            EntityModel<UserProfile> model = EntityModel.of(getUserProfileMediaType(userProfileDomain));
//
            WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getUsers());
            model.add(link.withRel("users"));
            link = linkTo(methodOn(this.getClass()).getUser(userProfileDomain.getId()));
            model.add(link.withRel("self"));
            return new ResponseEntity<>(model,HttpStatus.OK);
        }
        throw new NotFoundException(StatusCode._404.getDescription());
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> deleteUser(@PathVariable String id){
        com.first.rest.webservices.domain.UserProfile userProfileDomain = deleteUserById(id);
        if(userProfileDomain!=null) {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
        throw new NotFoundException(StatusCode._404.getDescription());
    }


    private com.first.rest.webservices.domain.UserProfile populateUserProfileEntity(UserProfile userProfile){
        com.first.rest.webservices.domain.UserProfile newUserProfile = new com.first.rest.webservices.domain.UserProfile();
        BeanUtils.copyProperties(userProfile,newUserProfile);
        newUserProfile.setId(UUID.randomUUID().toString());
        return newUserProfile;
    }

    private UserProfile getUserProfileMediaType(com.first.rest.webservices.domain.UserProfile userProfileDomain){
        UserProfile userProfile =UserProfile.builder()
                .id(userProfileDomain.getId())
                .birthDate(userProfileDomain.getBirthDate())
                .firstName(userProfileDomain.getFirstName())
                .lastName(userProfileDomain.getLastName())
                .email(userProfileDomain.getEmail()).build();
        return userProfile;
    }

    private com.first.rest.webservices.domain.UserProfile getUserById(String id){
        for(int i=0;i<userProfilesDomain.size();i++){
            if(userProfilesDomain.get(i).getId().equals(id)){
                return userProfilesDomain.get(i);
            }
        }
        return null;
    }

    private com.first.rest.webservices.domain.UserProfile deleteUserById(String id){
        Iterator<com.first.rest.webservices.domain.UserProfile> iterator = userProfilesDomain.iterator();
        while(iterator.hasNext()){
            com.first.rest.webservices.domain.UserProfile userProfile = iterator.next();
            if(userProfile.getId().equals(id)){
                iterator.remove();
                return userProfile;
            }
        }
        return null;
    }

    private ResponseEntity<EntityModel<UserProfile>> getResponse(
            com.first.rest.webservices.domain.UserProfile userProfile, HttpStatus status) {

        UserProfile userProfileMediaType = getUserProfileMediaType(userProfile);
        EntityModel<UserProfile> userProfileEntityModel =
                EntityModel.of(userProfileMediaType);

        HttpHeaders headers = new HttpHeaders();
        LinkBuilder lb = entityLinks.linkFor(UserProfile.class);
        userProfileEntityModel.add(lb.slash(userProfileMediaType.getId()).withSelfRel());
        headers.setLocation(
                URI.create(lb.slash(userProfileMediaType.getId()).withSelfRel().getHref()));
        return new ResponseEntity<>(userProfileEntityModel, headers, status);
    }

}
