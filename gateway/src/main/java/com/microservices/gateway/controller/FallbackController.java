package com.microservices.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @GetMapping("/contact-support")
    public Mono<ResponseEntity<String>> contactSupport() {
        return Mono.just(ResponseEntity.badRequest().body("An error occurred. Please try after some time or contact support team !"));
    }
}
