package com.first.rest.webservices.controllers.jpa;

import com.first.rest.webservices.config.AppLogger;
import com.first.rest.webservices.controllers.BaseController;
import com.first.rest.webservices.controllers.ControllerMappings;
import com.first.rest.webservices.domain.UserProfile;
import com.first.rest.webservices.exception.constants.StatusCode;
import com.first.rest.webservices.exception.exceptions.BadRequestException;
import com.first.rest.webservices.exception.exceptions.NotFoundException;
import com.first.rest.webservices.mediatype.Post;
import com.first.rest.webservices.service.PostService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = ControllerMappings.POSTS_JPA)
public class UserPostJpaController extends BaseController {

    private static final AppLogger LOGGER = AppLogger.getLogger(com.first.rest.webservices.controllers.jpa.UserPostJpaController.class);

    @Autowired
    private PostService postService;

    @RequestMapping(method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EntityModel<Post>>> getPosts(@PathVariable String userId){
        Optional<UserProfile> userProfile=userProfileRepository.findById(userId);

        if(!userProfile.isPresent()){
            throw new NotFoundException(StatusCode._404.getDescription(),
                    String.format("The User with id [ %s ] not found",userId));
        }
        List<com.first.rest.webservices.domain.Post> userPostDomainList =
                postService.getPostsByUserId(userId);
        List<EntityModel<Post>> userPosts=new ArrayList<>();
        userPostDomainList.forEach(postDomain -> {
            Post postMediaType = getPostMediaType(postDomain);
            EntityModel<Post> model = EntityModel.of(postMediaType);
            WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getPosts(postMediaType.getId()));
            model.add(link.withRel("self"));
            link = linkTo(methodOn(UserProfileJpaController.class).getUser(userId));
            model.add(link.withRel("users"));
            userPosts.add(model);
        });
        return new ResponseEntity<>(userPosts, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{postId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Post>> getPostById(@PathVariable String userId,
                                                               @PathVariable String postId){
        Optional<UserProfile> userProfile=userProfileRepository.findById(userId);

        if(!userProfile.isPresent()){
            throw new NotFoundException(StatusCode._404.getDescription(),
                    String.format("The User with id [ %s ] not found",userId));
        }
        com.first.rest.webservices.domain.Post postDomainType =
                postService.getPostsByIdAndUserId(postId,userId);

        if(Objects.isNull(postDomainType)){
            throw new NotFoundException(StatusCode._404.getDescription(),
                    String.format("The Post with id [ %s ] not found",postId));
        }
        Post postMediaType= getPostMediaType(postDomainType);
        EntityModel<Post> model = EntityModel.of(postMediaType);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass())
                .getPostById(postDomainType.getUserProfile().getId(),postMediaType.getId()));
        model.add(link.withRel("self"));
        link = linkTo(methodOn(this.getClass()).getPosts(userId));
        model.add(link.withRel("posts"));
        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Post>> createPost(@PathVariable String userId,
                                                        @RequestBody Post post){
        Optional<UserProfile> userProfile=userProfileRepository.findById(userId);

        if(!userProfile.isPresent()){
            throw new NotFoundException(StatusCode._404.getDescription(),
                    String.format("The User with id [ %s ] not found",userId));
        }

        if(StringUtils.isBlank(post.getTitle())){
            throw new BadRequestException(StatusCode._400.getDescription(),"The post title must not be empty");
        }
        com.first.rest.webservices.domain.Post postDomainType =
                postService.createPost(userProfile.get(),post);
        Post postMediaType= getPostMediaType(postDomainType);
        EntityModel<Post> model = EntityModel.of(postMediaType);
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass())
                .getPostById(postDomainType.getUserProfile().getId(),postMediaType.getId()));
        model.add(link.withRel("self"));
        link = linkTo(methodOn(this.getClass()).getPosts(userId));
        model.add(link.withRel("posts"));
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<Post>> deletePost(@PathVariable String userId,
                                                        @PathVariable String postId){
        Optional<UserProfile> userProfile=userProfileRepository.findById(userId);

        if(!userProfile.isPresent()){
            throw new NotFoundException(StatusCode._404.getDescription(),
                    String.format("The User with id [ %s ] not found",userId));
        }

        com.first.rest.webservices.domain.Post postDomainType =
                postService.getPostsByIdAndUserId(postId,userId);

        if(Objects.isNull(postDomainType)){
            throw new NotFoundException(StatusCode._404.getDescription(),
                    String.format("The Post with id [ %s ] not found",postId));
        }
        postService.deletePostsById(postId);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }





    private Post getPostMediaType(com.first.rest.webservices.domain.Post postDomain){
        Post postMedia = Post.builder()
                .id(postDomain.getId())
                .description(postDomain.getDescription())
                .title(postDomain.getTitle()).build();
        return postMedia;
    }
}
