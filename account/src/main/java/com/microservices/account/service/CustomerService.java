package com.microservices.account.service;

import com.microservices.account.service.dto.response.CustomerDetailsDto;

public interface CustomerService {

    CustomerDetailsDto getCustomerDetails(String mobileNumber, String correlationId);
}
