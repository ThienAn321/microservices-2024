package com.microservices.loan.constants;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonConstants {

    public static final String STATUS_201 = "201";
    public static final String STATUS_202 = "202";

    public static final Integer NEW_LOAN_LIMIT = 100_000;

    public static final String INVALID_VALIDATE = "Data invalid";

    public static final String MESSAGE_CREATED = "Loan created successfully";
    public static final String MESSAGE_UPDATED = "Loan updated successfully";
    public static final String MESSAGE_DELETED = "Loan deleted successfully";
}
