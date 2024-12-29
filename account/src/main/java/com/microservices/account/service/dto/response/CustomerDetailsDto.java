package com.microservices.account.service.dto.response;

import com.microservices.account.service.dto.request.CustomerDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerDetailsDto extends CustomerDto {

    private CardDto cardDto;
    private LoanDto loanDto;
}
