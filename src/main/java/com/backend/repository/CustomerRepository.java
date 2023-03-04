package com.backend.repository;

import com.backend.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository <Customer, Long> {
    List<Customer> findAll();
    Optional<Customer> findById(Long id);
}
