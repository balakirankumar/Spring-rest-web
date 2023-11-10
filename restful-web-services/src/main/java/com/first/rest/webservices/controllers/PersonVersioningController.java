package com.first.rest.webservices.controllers;


import com.first.rest.webservices.mediatype.Name;
import com.first.rest.webservices.mediatype.Post;
import com.first.rest.webservices.mediatype.UserProfile;
import com.first.rest.webservices.mediatype.UserProfileV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping(value = ControllerMappings.CONTEXT_PATH)
public class PersonVersioningController {

    @GetMapping(value = "/v1/person")
    public UserProfile userProfileV1(){

        return new UserProfile("v1/person","bala","Kiran","b@mail.com", "password@123",new Date(),new ArrayList<>());
    }

    @GetMapping(value = "/v2/person")
    public UserProfileV2 userProfileV2(){

        return new UserProfileV2("v2/person","bala",new Name("Bala","Kiran"),new Date());
    }

    @GetMapping(value = "/person/param",params = "version=v1")
    public UserProfile paramV1(){

        return new UserProfile("param=v1","bala","Bala","Kiran", "password@123",new Date(),new ArrayList<>());
    }

    @GetMapping(value = "/person/param",params = "version=v2")
    public UserProfileV2 paramV2(){

        return new UserProfileV2("param=v2","bala",new Name("Bala","Kiran"),new Date());
    }

    @GetMapping(value = "/person/header",headers = "X-API-VERSION=1")
    public UserProfile headerV1(){

        return new UserProfile("X-API-VERSION=1","bala","Bala","Kiran", "password@123",new Date(),new ArrayList<>());
    }

    @GetMapping(value = "/person/header",headers ="X-API--VERSION=2")
    public UserProfileV2 headerV2(){

        return new UserProfileV2("X-API--VERSION=2","bala",new Name("Bala","Kiran"),new Date());
    }

    @GetMapping(value = "/person/producer",produces = "application/v1+json")
    public UserProfile producerV1(){

        return new UserProfile("application/v1+json","bala","Bala","Kiran", "password@123",new Date(),new ArrayList<>());
    }

    @GetMapping(value = "/person/producer",produces = "application/v2+json")
    public UserProfileV2 producerV2(){

        return new UserProfileV2("application/v2+json","bala",new Name("Bala","Kiran"),new Date());
    }
}
