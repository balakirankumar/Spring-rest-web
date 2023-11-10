package com.first.rest.webservices.service.impl;

import com.first.rest.webservices.repository.*;
import com.first.rest.webservices.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Lazy
public class BeanInjectionService {

    @Autowired
    public EntityLinks entityLinks;

    @Autowired
    public UserProfileRepository userProfileRepository;

    @Autowired
    public PostsRepository postsRepository;

    @Autowired
    public PostService postService;

    @Autowired
    public RecipeRepository recipeRepository;

    @Autowired
    public IngredientRepository ingredientRepository;

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    public UserProfileRoleRepository userProfileRoleRepository;

    @Autowired
    public RoleService roleService;

    @Autowired
    public UserProfileRoleService userProfileRoleService;

    @Autowired
    public UserProfileService userProfileService;

    @Autowired
    public SecurityService securityService;

//    @Autowired
//    RestTemplate restTemplate;

}

