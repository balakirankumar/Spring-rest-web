package com.first.rest.webservices.controllers;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.first.rest.webservices.config.AppLogger;
import com.first.rest.webservices.domain.UserProfile;
import com.first.rest.webservices.exception.constants.StatusCode;
import com.first.rest.webservices.exception.exceptions.BadRequestException;
import com.first.rest.webservices.exception.exceptions.ForBiddenException;
import com.first.rest.webservices.service.impl.BeanInjectionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = ControllerMappings.REFRESH_TOKEN)
public class RefreshTokenController extends BeanInjectionService {

    public static AppLogger LOGGER = AppLogger.getLogger(RefreshTokenController.class);


    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    public void getNewToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(Objects.isNull(authorization)){
            throw new ForBiddenException(StatusCode._403.getDescription(),"Required a header with 'refresh-token'");
        }
        try {
            String token = authorization.substring("bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String username = decodedJWT.getSubject();
            UserProfile userProfile = userProfileService.getUserProfileById(username);
            if(Objects.isNull(userProfile)) {
                throw new BadRequestException("Token doest not contain valid email ","Token invalidated");
            }
            List<String> userRoles = new ArrayList<>();
            userProfileRoleService.findByUserProfileId(userProfile.getId()).forEach(userProfileRole->{
                userRoles.add(userProfileRole.getRole().getName());
            });
            String access_token = JWT.create()
                    .withSubject(userProfile.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis()+10*60*1000))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles", userRoles)
                    .sign(algorithm);
            response.setStatus(HttpStatus.OK.value());
            Map<String ,String> tokens = new HashMap<>();
            tokens.put("access_token",access_token);
            tokens.put("refresh_token",authorization);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),tokens);
        }
        catch (Exception exception){
            LOGGER.error("Exception occurred while filtering [ {} ]", exception);
            response.setHeader("error",exception.getMessage());
//                    response.sendError(HttpStatus.FORBIDDEN.value());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            Map<String ,String> tokens = new HashMap<>();
            tokens.put("error_message",exception.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(),tokens);

        }
    }


}
