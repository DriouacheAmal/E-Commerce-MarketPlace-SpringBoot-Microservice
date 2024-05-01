package com.example.Gateway.Config;

import com.example.Gateway.Filter.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ConfigRouter {
    private final JwtAuthenticationFilter filter;
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route("User", r -> r.path("/users/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://User"))

                .route("Catalogue", r -> r.path("/catalogue/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://Catalogue"))

                .route("Security", r -> r.path("/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://Security"))

                .route("Order", r -> r.path("/order/**")
                        .uri("lb://Order"))

                .build();

    }
}
