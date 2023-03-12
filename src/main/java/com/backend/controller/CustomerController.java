package com.backend.controller;

import com.backend.domain.Customer;
import com.backend.domain.dto.CustomerDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.CustomerMapper;
import com.backend.service.CustomerDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
@Validated
public class CustomerController {
    private final CustomerDbService customerDbService;
    private final CustomerMapper customerMapper;

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long customerId) throws MyEntityNotFoundException {
        Customer customer = customerDbService.getCustomer(customerId);
        return ResponseEntity.ok(customerMapper.mapToCustomerDto(customer));
    }

    @GetMapping
    public ResponseEntity<CustomerDto> getCustomer(@RequestParam String username) throws MyEntityNotFoundException {
        Customer customer = customerDbService.getCustomer(username);
        return ResponseEntity.ok(customerMapper.mapToCustomerDtoLogin(customer));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        customerDbService.saveCustomer(customerMapper.mapToCustomerLogin(customerDto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) throws MyEntityNotFoundException {
        customerDbService.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }
}
