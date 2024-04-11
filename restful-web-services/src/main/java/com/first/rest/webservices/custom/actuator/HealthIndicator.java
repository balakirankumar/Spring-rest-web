package com.first.rest.webservices.custom.actuator;


import com.first.rest.webservices.config.TimeLogger;
import com.first.rest.webservices.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.text.html.parser.Entity;
import java.util.List;

@Component
public class HealthIndicator extends BaseController implements org.springframework.boot.actuate.health.HealthIndicator {

    @Autowired
    EntityManager entityManager;
    @Override
    public Health getHealth(boolean includeDetails) {
        return org.springframework.boot.actuate.health.HealthIndicator.super.getHealth(includeDetails);
    }

    @TimeLogger
    @Override
    public Health health() {
        Query query = entityManager.createNativeQuery("select * from rest.schema_version");
        List object = query.getResultList();
        System.out.println(object.get(0).toString());
        metricsIndicator.longRunningOperation();
        return Health.down().withDetail("response_code",500).build();
    }
}
