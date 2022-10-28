package com.first.rest.webservices.controllers;


import com.first.rest.webservices.mediatype.Name;
import com.first.rest.webservices.mediatype.UserProfile;
import com.first.rest.webservices.mediatype.UserProfileV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = ControllerMappings.CONTEXT_PATH)
public class PersonVersioningController {

    @GetMapping(value = "/v1/person")
    public UserProfile userProfileV1(){

        return new UserProfile("id","bala","Kiran","b@mail.com", new Date(),null);
    }

    @GetMapping(value = "/v2/person")
    public UserProfileV2 userProfileV2(){

        return new UserProfileV2("id","bala",new Name("Bala","Kiran"),new Date());
    }

    @GetMapping(value = "/person/param",params = "version=v1")
    public UserProfile paramV1(){

        return new UserProfile("id","bala","Bala","Kiran",new Date(),null);
    }

    @GetMapping(value = "/person/param",params = "version=v2")
    public UserProfileV2 paramV2(){

        return new UserProfileV2("id","bala",new Name("Bala","Kiran"),new Date());
    }

    @GetMapping(value = "/person/header",headers = "X-API-VERSION=1")
    public UserProfile headerV1(){

        return new UserProfile("id","bala","Bala","Kiran",new Date(),null);
    }

    @GetMapping(value = "/person/header",headers ="X-API--VERSION=2")
    public UserProfileV2 headerV2(){

        return new UserProfileV2("id","bala",new Name("Bala","Kiran"),new Date());
    }

    @GetMapping(value = "/person/producer",produces = "application/v1+json")
    public UserProfile producerV1(){

        return new UserProfile("id","bala","Bala","Kiran",new Date(),null);
    }

    @GetMapping(value = "/person/producer",produces = "application/v2+json")
    public UserProfileV2 producerV2(){

        return new UserProfileV2("id","bala",new Name("Bala","Kiran"),new Date());
    }
}
