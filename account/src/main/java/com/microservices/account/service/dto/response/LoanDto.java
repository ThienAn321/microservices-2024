package com.microservices.account.service.dto.response;

import lombok.Data;

@Data
public class LoanDto {

    private String mobileNumber;
    private String loanNumber;
    private String loanType;
    private Integer totalLoan;
    private Integer amountPaid;
    private Integer outstandingAmount;
}
