package com.microservices.loan.service.impl;

import com.microservices.loan.constants.CommonConstants;
import com.microservices.loan.entity.Loan;
import com.microservices.loan.enumeration.LoanType;
import com.microservices.loan.exception.custom.DataNotFoundException;
import com.microservices.loan.exception.custom.LoanAlreadyExistsException;
import com.microservices.loan.repository.LoanRepository;
import com.microservices.loan.service.LoanService;
import com.microservices.loan.service.dto.request.LoanDto;
import com.microservices.loan.service.mapper.LoanMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;

    @Override
    public LoanDto getLoanDetails(String mobileNumber) {
        return loanRepository.findByMobileNumber(mobileNumber)
                .map(loanMapper::toLoanResponseDto)
                .orElseThrow(() -> new DataNotFoundException("Loan", "mobileNumber", mobileNumber));
    }

    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loan> optionalLoan = loanRepository.findByMobileNumber(mobileNumber);
        if (optionalLoan.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber: " + mobileNumber);
        }
        loanRepository.save(this.generateLoanEntity(mobileNumber));
    }

    @Override
    public void updateLoan(LoanDto loanDto) {
        Loan loan = loanRepository.findByLoanNumber(loanDto.getLoanNumber())
                .map(existingLoan -> loanMapper.updateEntity(existingLoan, loanDto))
                .map(loanRepository::save)
                .orElseThrow(() -> new DataNotFoundException("Loan", "LoanNumber", loanDto.getLoanNumber()));
        loanRepository.save(loan);
    }

    @Override
    public void deleteLoan(String mobileNumber) {
        Long loanId = loanRepository.findByMobileNumber(mobileNumber)
                .map(Loan::getLoanId)
                .orElseThrow(() -> new DataNotFoundException("Loan", "mobileNumber", mobileNumber));
        loanRepository.deleteById(loanId);
    }

    private Loan generateLoanEntity(String mobileNumber) {
        Loan loan = new Loan();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        loan.setLoanNumber(Long.toString(randomLoanNumber));
        loan.setMobileNumber(mobileNumber);
        loan.setLoanType(LoanType.HOME_LOAN);
        loan.setTotalLoan(CommonConstants.NEW_LOAN_LIMIT);
        loan.setAmountPaid(0);
        loan.setOutstandingAmount(CommonConstants.NEW_LOAN_LIMIT);
        return loan;
    }
}
