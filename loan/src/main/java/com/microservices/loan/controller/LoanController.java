package com.microservices.loan.controller;

import com.microservices.loan.constants.CommonConstants;
import com.microservices.loan.service.LoanService;
import com.microservices.loan.service.dto.request.LoanDto;
import com.microservices.loan.service.dto.response.LoanContactInfoDto;
import com.microservices.loan.service.dto.response.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
@Slf4j
public class LoanController {

    private final LoanService loanService;
    private final LoanContactInfoDto loanContactInfoDto;

    @Value("${build.version}")
    private String buildVersion;

    @GetMapping("/details")
    public ResponseEntity<LoanDto> getLoanDetails(@RequestHeader("correlation-id") String correlationId, @RequestParam String mobileNumber) {
//        log.debug("Correlation-id found: {}", correlationId);
        log.debug("getLoanDetails method start");
        LoanDto loanDto = loanService.getLoanDetails(mobileNumber);
        log.debug("getLoanDetails method end");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loanDto);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam String mobileNumber) {
        loanService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CommonConstants.STATUS_201, CommonConstants.MESSAGE_CREATED));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoanDetails(@RequestBody @Valid LoanDto loansDto) {
        loanService.updateLoan(loansDto);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new ResponseDto(CommonConstants.STATUS_202, CommonConstants.MESSAGE_UPDATED));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam String mobileNumber) {
        loanService.deleteLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new ResponseDto(CommonConstants.STATUS_202, CommonConstants.MESSAGE_DELETED));
    }

    @GetMapping("/version")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("/contact-info")
    public ResponseEntity<LoanContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loanContactInfoDto);
    }
}
