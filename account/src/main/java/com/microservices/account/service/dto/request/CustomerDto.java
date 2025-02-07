package com.microservices.account.service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @Size(min = 5, max = 50, message = "The length of the customer name should be between 5 and 50")
    private String name;

    @NotEmpty(message = "Email can not be a null or empty")
    @Email(message = "Email should be a valid value")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    private AccountDto accountDto;
}
