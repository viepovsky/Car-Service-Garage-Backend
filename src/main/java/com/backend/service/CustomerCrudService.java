package com.backend.service;

import com.backend.domain.Customer;
import com.backend.exceptions.CustomerNotFoundException;
import com.backend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerCrudService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(Long customerId) throws CustomerNotFoundException {
        return customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) throws CustomerNotFoundException {
        if(customerRepository.findById(customer.getId()).isPresent()) {
            return customerRepository.save(customer);
        } else {
            throw new CustomerNotFoundException();
        }
    }

    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        if(customerRepository.findById(customerId).isPresent()) {
            customerRepository.deleteById(customerId);
        } else {
            throw new CustomerNotFoundException();
        }
    }
}
