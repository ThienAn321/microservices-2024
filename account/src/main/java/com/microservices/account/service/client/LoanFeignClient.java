package com.microservices.account.service.client;

import com.microservices.account.service.dto.response.LoanDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("loan")
public interface LoanFeignClient {

    @GetMapping("/api/v1/loans/details")
    ResponseEntity<LoanDto> getLoanDetails(@RequestParam String mobileNumber);
}
