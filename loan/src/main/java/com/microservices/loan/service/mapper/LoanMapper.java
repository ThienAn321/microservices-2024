package com.microservices.loan.service.mapper;

import com.microservices.loan.entity.Loan;
import com.microservices.loan.enumeration.LoanType;
import com.microservices.loan.service.dto.request.LoanDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanMapper {

    LoanDto toLoanResponseDto(Loan loan);

    Loan toEntity(LoanDto loanDto);

    default Loan updateEntity(Loan loan, LoanDto loanDto) {
        if (loanDto.getMobileNumber() != null) {
            loan.setMobileNumber(loanDto.getMobileNumber());
        }
        if (loanDto.getLoanNumber() != null) {
            loan.setLoanNumber(loanDto.getLoanNumber());
        }
        if (loanDto.getLoanType() != null) {
            loan.setLoanType(LoanType.valueOf(loanDto.getLoanType()));
        }
        if (loanDto.getTotalLoan() != null && loanDto.getTotalLoan() > 0) {
            loan.setTotalLoan(loanDto.getTotalLoan());
        }
        if (loanDto.getAmountPaid() != null && loanDto.getAmountPaid() >= 0) {
            loan.setAmountPaid(loanDto.getAmountPaid());
        }
        if (loanDto.getOutstandingAmount() != null && loanDto.getOutstandingAmount() >= 0) {
            loan.setOutstandingAmount(loanDto.getOutstandingAmount());
        }
        return loan;
    }
}
