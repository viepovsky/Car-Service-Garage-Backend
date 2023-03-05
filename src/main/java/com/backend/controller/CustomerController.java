package com.backend.controller;

import com.backend.domain.Customer;
import com.backend.domain.dto.CustomerDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.CustomerMapper;
import com.backend.service.CustomerDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerDbService customerDbService;
    private final CustomerMapper customerMapper;

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long customerId) throws MyEntityNotFoundException {
        Customer customer = customerDbService.getCustomer(customerId);
        return ResponseEntity.ok(customerMapper.mapToCustomerDto(customer));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerDto customerDto) {
        customerDbService.saveCustomer(customerMapper.mapToCustomer(customerDto));
        return ResponseEntity.ok().build();
    }
}
