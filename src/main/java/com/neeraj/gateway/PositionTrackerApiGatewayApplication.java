package com.neeraj.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class PositionTrackerApiGatewayApplication {

    @GetMapping("/health")
    public String healthCheck() {
        return "Hey API Gateway is healthy.....!";
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("tracker", r -> r.path("/**")
//                        .uri("http://localhost:8090")
                                .uri("lb://tracker") // Client side load balancing using Netflix Ribbon
                )
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(PositionTrackerApiGatewayApplication.class, args);
    }


}
