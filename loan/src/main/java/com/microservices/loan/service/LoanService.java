package com.microservices.loan.service;

import com.microservices.loan.service.dto.request.LoanDto;

public interface LoanService {

    LoanDto getLoanDetails(String mobileNumber);

    void createLoan(String mobileNumber);

    void updateLoan(LoanDto loanDto);

    void deleteLoan(String mobileNumber);
}
