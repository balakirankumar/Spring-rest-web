package com.first.rest.webservices.controllers.jpa;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.first.rest.webservices.config.AppLogger;
import com.first.rest.webservices.config.TimeLogger;
import com.first.rest.webservices.controllers.BaseController;
import com.first.rest.webservices.controllers.ControllerMappings;
import com.first.rest.webservices.exception.constants.StatusCode;
import com.first.rest.webservices.exception.exceptions.BadRequestException;
import com.first.rest.webservices.exception.exceptions.NotFoundException;
import com.first.rest.webservices.mediatype.UserProfile;
import com.first.rest.webservices.service.UserProfileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.hateoas.server.LinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping(value = ControllerMappings.USERS_JPA)
@ExposesResourceFor(UserProfile.class)
public class UserProfileJpaController extends BaseController {

    private static final AppLogger LOGGER = AppLogger.getLogger(com.first.rest.webservices.controllers.jpa.UserProfileJpaController.class);

    @Autowired
    private UserProfileService userProfileService;

    public static List<com.first.rest.webservices.domain.UserProfile> userProfilesDomain
            =new ArrayList<>();

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<UserProfile>> createUser(@Valid @RequestBody UserProfile userProfile){

        com.first.rest.webservices.domain.UserProfile userProfile1 = userProfileService.findByEmail(userProfile.getEmail());
        if(Objects.nonNull(userProfile1)){
            throw new BadRequestException(StatusCode._400.getDescription(),"User with email exists try using other email or login");
        }
        com.first.rest.webservices.domain.UserProfile userProfileDomain =
                populateUserProfileEntity(userProfile);
        LOGGER.info("User profile has been created [ {} ]",userProfileDomain);

        userProfileDomain = userProfileService.createUserProfile(userProfileDomain);
        userProfile = getUserProfileMediaType(userProfileDomain);
        EntityModel<UserProfile> model = EntityModel.of(userProfile);
        WebMvcLinkBuilder  link = linkTo(methodOn(this.getClass()).getUser(userProfile.getId()));
        model.add(link.withRel("self"));

        return new ResponseEntity<>(model, HttpStatus.CREATED);

    }


    @TimeLogger
    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EntityModel<UserProfile>>> getUsers(
            @RequestParam(name = "page", required = false,defaultValue = "1") int page,
            @RequestParam(name = "items-per-page", required = false,defaultValue = "10") int itemsPerPage
    ){

        Pageable pageable = PageRequest.of(page-1,itemsPerPage);
        List<com.first.rest.webservices.domain.UserProfile> userProfileDomainList =
                userProfileService.getUserProfiles(pageable).getContent();
        List<EntityModel<UserProfile>> userProfileList=new ArrayList<>();
        userProfileDomainList.forEach(userProfile -> {
                UserProfile userProfileMediaType = getUserProfileMediaType(userProfile);
                EntityModel<UserProfile> model = EntityModel.of(userProfileMediaType);
                WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getUser(userProfileMediaType.getId()));
                model.add(link.withRel("self"));
                userProfileList.add(model);
                });
        return new ResponseEntity<>(userProfileList, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<UserProfile>> getUser(
            @PathVariable String id){
        com.first.rest.webservices.domain.UserProfile userProfileDomain = userProfileService.getUserProfileById(id);
        if(userProfileDomain!=null){
            LOGGER.info("User profile with id [ {} ] found",id);
            UserProfile userProfileMediaType=getUserProfileMediaType(userProfileDomain);
            EntityModel<UserProfile> model = EntityModel.of(userProfileMediaType);
            WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getUser(userProfileDomain.getId()));
            model.add(link.withRel("self"));
            return new ResponseEntity<>(model,HttpStatus.OK);
        }
        throw new NotFoundException(StatusCode._404.getDescription());
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserProfile> deleteUser(@PathVariable String id){
        com.first.rest.webservices.domain.UserProfile userProfileDomain = userProfileService.getUserProfileById(id);
        if(userProfileDomain!=null) {
            userProfileService.deleteUserProfile(id);
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

    private com.first.rest.webservices.mediatype.Post getPostMediaType(com.first.rest.webservices.domain.Post postDomain){
        com.first.rest.webservices.mediatype.Post postMedia = com.first.rest.webservices.mediatype.Post.builder()
                .id(postDomain.getId())
                .description(postDomain.getDescription())
                .title(postDomain.getTitle()).build();
        return postMedia;
    }

}
