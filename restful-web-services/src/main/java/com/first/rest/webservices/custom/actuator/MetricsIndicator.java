package com.first.rest.webservices.custom.actuator;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Service;

@Service
public class MetricsIndicator {

    private final Timer timer;

    public  MetricsIndicator(MeterRegistry registry){
        timer = registry.timer("long.running.op.timer");
    }

    public void longRunningOperation(){
        timer.record(()->{
            for (int i = 0; i < 100000; i++) {
//                System.out.println("loop "+i);
            }
            //long running operation
        });
    }

}
