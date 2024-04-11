package com.first.rest.webservices.security;

import com.first.rest.webservices.controllers.ControllerMappings;
import com.first.rest.webservices.filters.CustomAuthenticationFilter;
import com.first.rest.webservices.filters.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    String objId = "[0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}";

    public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    protected  void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl(ControllerMappings.TOKEN);
        httpSecurity.csrf().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.authorizeRequests().regexMatchers(ControllerMappings.TOKEN+ "(\\?.*)?").permitAll();
        httpSecurity.authorizeRequests().regexMatchers("/actuator"+ "(\\?.*)?").permitAll();

        httpSecurity.authorizeRequests()
                .regexMatchers(
                        ControllerMappings.REFRESH_TOKEN +"(\\?.*)?")
                .permitAll();

        httpSecurity.authorizeRequests()
                .regexMatchers(
                        ControllerMappings.USERS_JPA +"(\\?.*)?")
                .permitAll();

        httpSecurity.authorizeRequests()
                .antMatchers(
                        ControllerMappings.USERS + "/" + objId + "(\\?.*)?")
                .authenticated();

        httpSecurity.authorizeRequests()
                .regexMatchers(
                        ControllerMappings.ROLES +"(\\?.*)?")
                .authenticated();

//        httpSecurity.authorizeRequests().anyRequest().authenticated();

        httpSecurity.addFilter(customAuthenticationFilter);
        httpSecurity.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
}
