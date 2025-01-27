package com.microservices.account.service.client;

import com.microservices.account.service.client.fallback.CardFallback;
import com.microservices.account.service.dto.response.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "card", fallback = CardFallback.class)
public interface CardFeignClient {

    @GetMapping("/api/v1/cards/details")
    ResponseEntity<CardDto> getCardDetails(@RequestHeader("correlation-id") String correlationId, @RequestParam String mobileNumber);
}
