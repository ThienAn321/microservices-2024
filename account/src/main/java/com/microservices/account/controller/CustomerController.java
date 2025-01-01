package com.microservices.account.controller;

import com.microservices.account.service.CustomerService;
import com.microservices.account.service.dto.request.CustomerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/details")
    public ResponseEntity<CustomerDto> getCustomerDetails(@RequestHeader("correlation-id") String correlationId, @RequestParam String mobileNumber) {
        log.debug("Correlation-id found: {}", correlationId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.getCustomerDetails(mobileNumber, correlationId));
    }
}
