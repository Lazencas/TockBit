package org.example.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/auth/login")
                        .filters(f -> f.removeRequestHeader("Cookie")) // 전역 필터 제거
                        .uri("lb://user-service"))
                .route("user-service", r -> r.path("/auth/register")
                        .filters(f -> f.removeRequestHeader("Cookie")) // 전역 필터 제거
                        .uri("lb://user-service"))
//                .route("activity-service", r -> r.path("/activity/**")
//                        .uri("lb://activity-service"))
//                .route("newsfeed-service", r -> r.path("/newsfeed")
//                        .uri("lb://newsfeed-service"))
                .build();
    }
}
