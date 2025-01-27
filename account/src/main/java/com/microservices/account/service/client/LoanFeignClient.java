package com.microservices.account.service.client;

import com.microservices.account.service.client.fallback.LoanFallback;
import com.microservices.account.service.dto.response.LoanDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "loan", fallback = LoanFallback.class)
public interface LoanFeignClient {

    @GetMapping("/api/v1/loans/details")
    ResponseEntity<LoanDto> getLoanDetails(@RequestHeader("correlation-id") String correlationId, @RequestParam String mobileNumber);
}
