package org.example;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()

                // student info Service
                .route(p -> p.path("/api/students/**")
                        .uri("lb://STUDENT-INFO-SERVICE"))

                // grade data Service
                .route(p -> p.path("/api/courses**")
                        .uri("lb://GRADES-DATA-SERVICE"))
                .route(p -> p.path("/api/grades/**")
                        .uri("lb://GRADES-DATA-SERVICE"))

                // student catalog Service
                .route(p -> p.path("/api/catalogs/**")
                        .uri("lb://STUDENT-CATALOG-SERVICE"))

                .build();
    }
}