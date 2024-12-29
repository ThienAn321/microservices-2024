package com.microservices.account.service.dto.response;

import lombok.Data;

@Data
public class CardDto {

    private String mobileNumber;
    private String cardNumber;
    private String cardType;
    private Integer totalLimit;
    private Integer amountUsed;
    private Integer availableAmount;
}
