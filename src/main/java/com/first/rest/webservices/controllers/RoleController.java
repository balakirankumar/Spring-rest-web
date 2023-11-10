package com.first.rest.webservices.controllers;


import com.first.rest.webservices.domain.Role;
import com.first.rest.webservices.service.impl.BeanInjectionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = ControllerMappings.ROLES)
public class RoleController extends BeanInjectionService {
    
    @RequestMapping(method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<com.first.rest.webservices.mediatype.Role>> getAllRoles(){
        return  new ResponseEntity<>(roleService.getMediaTypeList(roleService.findAll()), HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<com.first.rest.webservices.mediatype.Role> saveRole(
            @RequestBody com.first.rest.webservices.mediatype.Role role){
        Role roleDomain = roleService.saveRole(role);
        return  new ResponseEntity<>(roleService.getMediaType(roleDomain), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{roleId}",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<com.first.rest.webservices.mediatype.Role> getRoleById(
            @PathVariable String roleId){
        return  new ResponseEntity<>(roleService.getMediaType(roleService.findById(roleId)), HttpStatus.CREATED);
    }

}
