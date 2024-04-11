package com.first.rest.webservices.custom.actuator;


import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "readiness")
public class OwnEndpoint {



    private String ready= "NOT_READY";

    @ReadOperation
//    @WriteOperation
//    @DeleteOperation
    public String getReadiness(){
        return ready;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setReady(){
        ready = "READY";
    }
}
