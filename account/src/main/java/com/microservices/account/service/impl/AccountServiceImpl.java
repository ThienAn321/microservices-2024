package com.microservices.account.service.impl;

import com.microservices.account.constant.CommonConstants;
import com.microservices.account.entity.Account;
import com.microservices.account.entity.Customer;
import com.microservices.account.enumeration.AccountType;
import com.microservices.account.exception.custom.CustomerAlreadyExistsException;
import com.microservices.account.exception.custom.DataNotFoundException;
import com.microservices.account.repository.AccountRepository;
import com.microservices.account.repository.CustomerRepository;
import com.microservices.account.service.AccountService;
import com.microservices.account.service.dto.request.AccountDto;
import com.microservices.account.service.dto.request.CustomerDto;
import com.microservices.account.service.mapper.AccountMapper;
import com.microservices.account.service.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountMapper accountMapper;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerDto getAccountDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new DataNotFoundException("Customer", "mobileNumber", mobileNumber));
        AccountDto accountDto = accountRepository.findByCustomerId(customer.getCustomerId())
                .map(accountMapper::toAccountResponseDto)
                .orElseThrow(() -> new DataNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
        CustomerDto customerDto = customerMapper.toCustomerResponseDto(customer);
        customerDto.setAccountDto(accountDto);
        return customerDto;
    }

    @Override
    public void createAccount(CustomerDto customerDto) {
        if (customerRepository.findByMobileNumber(customerDto.getMobileNumber()).isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists with number: " + customerDto.getMobileNumber());
        }

        Customer customer = customerMapper.toEntity(customerDto);
        Long savedCustomerId = customerRepository.save(customer).getCustomerId();
        accountRepository.save(createNewAccount(savedCustomerId));
        log.info("Created new account with id: {}", savedCustomerId);
    }

    @Override
    public void updateAccount(CustomerDto customerDto) {
        AccountDto accountDto = customerDto.getAccountDto();
        if (accountDto == null) {
            throw new IllegalArgumentException("Account must not be null");
        }

        Account account = accountRepository.findById(customerDto.getAccountDto().getAccountNumber())
                .map(existingAccount -> accountMapper.updateEntity(existingAccount, accountDto))
                .map(accountRepository::save)
                .orElseThrow(() -> new DataNotFoundException("Account", "AccountNumber", accountDto.getAccountNumber().toString()));

        Long customerId = account.getCustomerId();
        customerRepository.findById(customerId)
                .map(existingCustomer -> customerMapper.updateEntity(existingCustomer, customerDto))
                .map(customerRepository::save)
                .orElseThrow(() -> new DataNotFoundException("Customer", "CustomerID", customerId.toString()));
    }

    @Override
    @Transactional
    public void deleteAccount(String mobileNumber) {
        Long customerId = customerRepository.findByMobileNumber(mobileNumber)
                .map(Customer::getCustomerId)
                .orElseThrow(() -> new DataNotFoundException("Customer", "mobileNumber", mobileNumber));
        accountRepository.deleteByCustomerId(customerId);
        customerRepository.deleteById(customerId);
    }

    private Account createNewAccount(Long customerId) {
        Account account = new Account();
        account.setCustomerId(customerId);
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        account.setAccountNumber(randomAccNumber);
        account.setAccountType(AccountType.SAVINGS);
        account.setBranchAddress(CommonConstants.ADDRESS);
        return account;
    }
}
