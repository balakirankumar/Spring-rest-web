package com.first.rest.webservices.service.impl;

import com.first.rest.webservices.repository.PostsRepository;
import com.first.rest.webservices.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;


public class BeanInjectionService {

    @Autowired
    public EntityLinks entityLinks;

    @Autowired
    public UserProfileRepository userProfileRepository;

    @Autowired
    public PostsRepository postsRepository;
}

