package com.first.rest.webservices.custom.actuator;


import com.first.rest.webservices.service.impl.BeanInjectionService;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MaxMemoryHealthIndicator extends BeanInjectionService implements HealthIndicator {


    @Override
    public Health health() {
        long memory = Runtime.getRuntime().maxMemory();
        System.out.println(memory);
        boolean invalid = Runtime.getRuntime().maxMemory() > (100*1024*1024);
        Status status = invalid ? Status.DOWN: Status.UP;
        ResponseEntity<String> response = restTemplate.getForEntity("https://google.com",String.class);
        response.getBody();
        return Health.status(status).withDetail("google.com",response.getBody()).build();
    }
}
