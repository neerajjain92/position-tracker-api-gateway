package com.neeraj.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients(basePackageClasses = com.neeraj.gateway.service.RemoteService.class)
@EnableScheduling
public class PositionTrackerApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PositionTrackerApiGatewayApplication.class, args);
    }
}
