package com.microservices.account.service.mapper;

import com.microservices.account.entity.Account;
import com.microservices.account.enumeration.AccountType;
import com.microservices.account.service.dto.request.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    AccountDto toAccountResponseDto(Account account);

    Account toEntity(AccountDto accountDto);

    default Account updateEntity(Account account, AccountDto accountDto) {
        if (accountDto.getAccountNumber() != null) {
            account.setAccountNumber(accountDto.getAccountNumber());
        }
        if (accountDto.getAccountType() != null) {
            account.setAccountType(AccountType.valueOf(accountDto.getAccountType()));
        }
        if (accountDto.getBranchAddress() != null) {
            account.setBranchAddress(accountDto.getBranchAddress());
        }
        return account;
    }
}
