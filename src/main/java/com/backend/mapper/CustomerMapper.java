package com.backend.mapper;

import com.backend.domain.Customer;
import com.backend.domain.dto.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerMapper {

    public CustomerDto mapToCustomerDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getCreatedDate()
        );
    }

    public Customer mapToCustomer(CustomerDto customerDto) {
        return new Customer(
                customerDto.getId(),
                customerDto.getFirstName(),
                customerDto.getLastName(),
                customerDto.getEmail(),
                customerDto.getPhoneNumber(),
                customerDto.getCreatedDate(),
                null,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }
}
