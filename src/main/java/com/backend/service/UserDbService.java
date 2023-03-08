package com.backend.service;

import com.backend.domain.Customer;
import com.backend.domain.Garage;
import com.backend.domain.User;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.CustomerRepository;
import com.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDbService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUser(Long userId) throws MyEntityNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new MyEntityNotFoundException("User", userId));
    }

    public void saveUser(User user, Long customerId) throws MyEntityNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new MyEntityNotFoundException("Customer", customerId));
        user.setCustomer(customer);
        customer.setUser(user);
        customerRepository.save(customer);
    }

    public User updateUser(User user) throws MyEntityNotFoundException {
        if (userRepository.findById(user.getId()).isPresent()) {
            return userRepository.save(user);
        } else {
            throw new MyEntityNotFoundException("User", user.getId());
        }
    }

    public void deleteUser(Long userId) throws MyEntityNotFoundException {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new MyEntityNotFoundException("User", userId);
        }
    }
}
