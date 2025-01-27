package com.microservices.account.service.impl;

import com.microservices.account.entity.Customer;
import com.microservices.account.exception.custom.DataNotFoundException;
import com.microservices.account.repository.AccountRepository;
import com.microservices.account.repository.CustomerRepository;
import com.microservices.account.service.CustomerService;
import com.microservices.account.service.client.CardFeignClient;
import com.microservices.account.service.client.LoanFeignClient;
import com.microservices.account.service.dto.request.AccountDto;
import com.microservices.account.service.dto.response.CardDto;
import com.microservices.account.service.dto.response.CustomerDetailsDto;
import com.microservices.account.service.dto.response.LoanDto;
import com.microservices.account.service.mapper.AccountMapper;
import com.microservices.account.service.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountMapper accountMapper;
    private final CustomerMapper customerMapper;
    private final CardFeignClient cardFeignClient;
    private final LoanFeignClient loanFeignClient;

    @Override
    public CustomerDetailsDto getCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new DataNotFoundException("Customer", "mobileNumber", mobileNumber));
        AccountDto accountDtoResponse = accountRepository.findByCustomerId(customer.getCustomerId())
                .map(accountMapper::toAccountResponseDto)
                .orElseThrow(() -> new DataNotFoundException("Account", "customerId", customer.getCustomerId().toString()));

        CustomerDetailsDto customerDetailsDto = customerMapper.toCustomerDetailsDto(customer);
        customerDetailsDto.setAccountDto(accountDtoResponse);
        ResponseEntity<CardDto> cardDtoResponse = cardFeignClient.getCardDetails(correlationId, mobileNumber);
        if (cardDtoResponse != null) {
            customerDetailsDto.setCardDto(cardDtoResponse.getBody());
        }
        ResponseEntity<LoanDto> loanDtoResponse = loanFeignClient.getLoanDetails(correlationId, mobileNumber);
        if (loanDtoResponse != null) {
            customerDetailsDto.setLoanDto(loanDtoResponse.getBody());
        }
        return customerDetailsDto;
    }
}
