package com.microservices.account.service.dto.request;

import com.microservices.account.enumeration.AccountType;
import com.microservices.account.validator.enums.ValidateEnumValue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountDto {

    @NotEmpty(message = "Account number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "AccountNumber must be 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "Account type can not be a null or empty")
    @ValidateEnumValue(enumClass = AccountType.class)
    private String accountType;

    @NotEmpty(message = "Address can not be a null or empty")
    private String branchAddress;
}
