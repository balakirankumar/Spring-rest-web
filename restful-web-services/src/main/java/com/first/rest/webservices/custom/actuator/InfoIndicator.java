package com.first.rest.webservices.custom.actuator;


import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class InfoIndicator implements InfoContributor {

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("version",1.0)
        .withDetail("ownedByTeam","Bala");
    }
}
