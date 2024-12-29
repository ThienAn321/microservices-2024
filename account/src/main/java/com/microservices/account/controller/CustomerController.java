package com.microservices.account.controller;

import com.microservices.account.service.CustomerService;
import com.microservices.account.service.dto.request.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/details")
    public ResponseEntity<CustomerDto> getCustomerDetails(@RequestParam String mobileNumber) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerService.getCustomerDetails(mobileNumber));
    }
}
