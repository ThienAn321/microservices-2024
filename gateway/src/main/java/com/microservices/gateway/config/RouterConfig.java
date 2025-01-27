package com.microservices.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {

    private final RedisRateLimiter redisRateLimiter;
    private final KeyResolver keyResolver;

    @Bean
    public RouteLocator routesConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
                        .path("/api/v1/accounts/**", "/api/v1/customers/**")
                        .filters(f -> f.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                //rewritePath("/(?<segment>.*)", "/${segment}")
                                .circuitBreaker(config -> config.setName("accountsCircuitBreaker")
                                .setFallbackUri("forward:/contact-support")
                                ))
                        .uri("lb://ACCOUNT"))
                .route(p -> p
                        .path("/api/v1/cards/**")
                        .filters(f -> f.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                        .requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter)
                                                .setKeyResolver(keyResolver))
                                //rewritePath("/(?<segment>.*)", "/${segment}")
                                )

                        .uri("lb://CARD"))
                .route(p -> p
                        .path("/api/v1/loans/**")
                        .filters(f -> f.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                //rewritePath("/(?<segment>.*)", "/${segment}")
                                .retry(retryConfig -> retryConfig.setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true))
                                )
                        .uri("lb://LOAN"))
                .build();
    }
}
