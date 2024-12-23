package com.microservices.account.service;

import com.microservices.account.service.dto.request.CustomerDto;

public interface AccountService {

    CustomerDto getAccountDetails(String mobileNumber);

    void createAccount(CustomerDto customerDto);

    void updateAccount(CustomerDto customerDto);

    void deleteAccount(String mobileNumber);
}
