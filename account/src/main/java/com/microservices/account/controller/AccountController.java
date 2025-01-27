package com.microservices.account.controller;

import com.microservices.account.constant.CommonConstants;
import com.microservices.account.service.AccountService;
import com.microservices.account.service.dto.request.CustomerDto;
import com.microservices.account.service.dto.response.AccountContactInfoDto;
import com.microservices.account.service.dto.response.ResponseDto;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
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

    @Retry(name = "getBuildVersion", fallbackMethod = "getBuildVersionFallback")
    @GetMapping("/version")
    public ResponseEntity<String> getBuildVersion() {
        log.debug("getBuildVersion() method invoked");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    private ResponseEntity<String> getBuildVersionFallback(Throwable throwable) {
        log.debug("getBuildVersionFallback() method invoked");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("0.1");
    }

    @RateLimiter(name = "getBuildVersion", fallbackMethod = "getJavaVersionFallback")
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        log.debug("getJavaVersion() method invoked");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("21");
    }

    private ResponseEntity<String> getJavaVersionFallback(Throwable throwable) {
        log.debug("getJavaVersionFallback() method invoked");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("17");
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountContactInfoDto);
    }
}
