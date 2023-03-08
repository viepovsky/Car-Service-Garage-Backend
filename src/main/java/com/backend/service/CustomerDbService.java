package com.backend.service;

import com.backend.domain.Customer;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerDbService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(Long customerId) throws MyEntityNotFoundException {
        return customerRepository.findById(customerId).orElseThrow(() -> new MyEntityNotFoundException("Customer", customerId));
    }

    public void saveCustomer(Customer customer) {
        if (customer.getCreatedDate() == null) {
            customer.setCreatedDate(LocalDateTime.now());
        }
        customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) throws MyEntityNotFoundException {
        if(customerRepository.findById(customer.getId()).isPresent()) {
            return customerRepository.save(customer);
        } else {
            throw new MyEntityNotFoundException("Customer", customer.getId());
        }
    }

    public void deleteCustomer(Long customerId) throws MyEntityNotFoundException {
        if(customerRepository.findById(customerId).isPresent()) {
            customerRepository.deleteById(customerId);
        } else {
            throw new MyEntityNotFoundException("Customer", customerId);
        }
    }
}
