package com.first.rest.webservices.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.first.rest.webservices.config.AppLogger;
import com.first.rest.webservices.controllers.ControllerMappings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    public static final AppLogger LOGGER = AppLogger.getLogger(CustomAuthenticationFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if( request.getServletPath().equals(ControllerMappings.TOKEN) ||
            request.getServletPath().equals(ControllerMappings.REFRESH_TOKEN) ||
            request.getServletPath().equals(ControllerMappings.USERS_JPA)){
            filterChain.doFilter(request,response);
        }
        else{
            String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorization!=null && authorization.startsWith("bearer ")){
                try {
                    String token = authorization.substring("bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    Arrays.stream(roles).forEach(role->{
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null,authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request,response);
                }
                catch (Exception exception){
                    LOGGER.error("Exception occured while filtering [ {} ]", exception);
                    response.setHeader("error",exception.getMessage());
//                    response.sendError(HttpStatus.FORBIDDEN.value());
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    Map<String ,String> tokens = new HashMap<>();
                    tokens.put("error_message",exception.getMessage());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(),tokens);

                }
            }
            else{
                filterChain.doFilter(request,response);
            }
        }
    }
}
