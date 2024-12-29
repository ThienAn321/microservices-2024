package com.microservices.account.service.mapper;

import com.microservices.account.entity.Customer;
import com.microservices.account.service.dto.request.CustomerDto;
import com.microservices.account.service.dto.response.CustomerDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    CustomerDto toCustomerResponseDto(Customer customer);

    CustomerDetailsDto toCustomerDetailsDto(Customer customer);

    Customer toEntity(CustomerDto customerDto);

    default Customer updateEntity(Customer customer, CustomerDto customerDto) {
        if (customerDto.getName() != null) {
            customer.setName(customerDto.getName());
        }
        if (customerDto.getEmail() != null) {
            customer.setEmail(customerDto.getEmail());
        }
        if (customerDto.getMobileNumber() != null) {
            customer.setMobileNumber(customerDto.getMobileNumber());
        }
        return customer;
    }
}
