package com.first.rest.webservices.mediatype;


import lombok.Data;

import java.util.Date;

@Data
public class UserProfileRole {


    private String id;
    private String roleId;
    private String roleName;
    private String roleDescription;
    private String userProfileId;
    private String createdBy;
    private Date createdDate;
}
