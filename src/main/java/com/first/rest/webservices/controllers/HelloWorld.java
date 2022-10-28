package com.first.rest.webservices.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = ControllerMappings.HELLO_WORLD)
public class HelloWorld {

    @Autowired
    private MessageSource messageSource;

//    @GetMapping(path = "/{name}")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> hello(){
        return new ResponseEntity<>("Hello-World", HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/{name}")
    public ResponseEntity<com.first.rest.webservices.mediatype.HelloWorld> helloBean(@PathVariable String name){
        return new ResponseEntity<>(
                new com.first.rest.webservices.mediatype.HelloWorld(String.format("Hello @%s",name),
                UUID.randomUUID().toString()), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET,value = "/{name}/international")
    public ResponseEntity<com.first.rest.webservices.mediatype.HelloWorld> helloBeanInternational(@PathVariable String name){
        messageSource.getMessage("value",null,"Default Message", LocaleContextHolder.getLocale());
        return new ResponseEntity<>(
                new com.first.rest.webservices.mediatype.HelloWorld(String.format("Hello @%s",name),
                        UUID.randomUUID().toString()), HttpStatus.CREATED);
    }
}
