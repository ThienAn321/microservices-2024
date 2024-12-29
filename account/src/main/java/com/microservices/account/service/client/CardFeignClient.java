package com.microservices.account.service.client;

import com.microservices.account.service.dto.response.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("card")
public interface CardFeignClient {

    @GetMapping("/api/v1/cards/details")
    ResponseEntity<CardDto> getCardDetails(@RequestParam String mobileNumber);
}
