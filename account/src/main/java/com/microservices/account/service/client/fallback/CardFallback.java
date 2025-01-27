package com.microservices.account.service.client.fallback;

import com.microservices.account.service.client.CardFeignClient;
import com.microservices.account.service.dto.response.CardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardFallback implements CardFeignClient {

    @Override
    public ResponseEntity<CardDto> getCardDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
