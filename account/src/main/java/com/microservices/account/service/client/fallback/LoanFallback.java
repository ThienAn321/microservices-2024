package com.microservices.account.service.client.fallback;

import com.microservices.account.service.client.LoanFeignClient;
import com.microservices.account.service.dto.response.LoanDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoanFallback implements LoanFeignClient {

    @Override
    public ResponseEntity<LoanDto> getLoanDetails(String correlationId, String mobileNumber) {
        return null;
    }
}
