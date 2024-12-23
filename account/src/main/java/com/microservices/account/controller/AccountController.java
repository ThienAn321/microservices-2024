package com.microservices.account.controller;

import com.microservices.account.constant.CommonConstants;
import com.microservices.account.service.AccountService;
import com.microservices.account.service.dto.request.CustomerDto;
import com.microservices.account.service.dto.response.AccountContactInfoDto;
import com.microservices.account.service.dto.response.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@RefreshScope
public class AccountController {

    private final AccountService accountService;
    private final AccountContactInfoDto accountContactInfoDto;

    @Value("${build.version}")
    private String buildVersion;

    @GetMapping("/details")
    public ResponseEntity<CustomerDto> getAccountDetails(@RequestParam String mobileNumber) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountService.getAccountDetails(mobileNumber));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody @Valid CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CommonConstants.STATUS_201, CommonConstants.MESSAGE_CREATED));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@RequestBody @Valid CustomerDto customerDto) {
        accountService.updateAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new ResponseDto(CommonConstants.STATUS_202, CommonConstants.MESSAGE_UPDATED));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam String mobileNumber) {
        accountService.deleteAccount(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new ResponseDto(CommonConstants.STATUS_202, CommonConstants.MESSAGE_DELETED));
    }

    @GetMapping("/version")
    public ResponseEntity<String> getBuildVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountContactInfoDto);
    }
}
