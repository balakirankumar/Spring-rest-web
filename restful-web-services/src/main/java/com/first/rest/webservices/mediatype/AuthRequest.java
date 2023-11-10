package com.first.rest.webservices.mediatype;


import lombok.Data;

@Data
public class AuthRequest {

    private String username;
    private String password;
}
