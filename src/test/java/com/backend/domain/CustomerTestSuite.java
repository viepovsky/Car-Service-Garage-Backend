package com.backend.domain;

import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.service.CustomerDbService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@DisplayName("Customer Entity Test Suite")
class CustomerTestSuite {

    @Autowired
    private CustomerDbService customerDbService;

    @Test
    public void testSaveAndRetrieveCustomer() throws MyEntityNotFoundException {
        //Given
        Customer customer = new Customer(1L, "Oskar", "Raj", "testmail@gmail.com", "+48756756756", LocalDateTime.now(), null, new ArrayList<>(), new ArrayList<>());
        customerDbService.saveCustomer(customer);
        //When
        List<Customer> customerList = customerDbService.getAllCustomers();
        Customer retrievedCustomer = customerList.get(0);
        //Then
        assertEquals(customer.getFirstName(), retrievedCustomer.getFirstName());
        assertEquals(customer.getEmail(), retrievedCustomer.getEmail());
        assertEquals(customer.getCreatedDate(), retrievedCustomer.getCreatedDate());
        assertDoesNotThrow(() -> customerDbService.getCustomer(retrievedCustomer.getId()));
        System.out.println(retrievedCustomer.getId());
    }

    @Test
    public void testUpdateAndDeleteCustomer() throws MyEntityNotFoundException {
        //Given
        Customer customer = new Customer(1L, "Mariusz", "Raj", "h2base@gmail.com", "+48756756756", LocalDateTime.now(), null, new ArrayList<>(), new ArrayList<>());
        customerDbService.saveCustomer(customer);
        List<Customer> customerList = customerDbService.getAllCustomers();
        Customer customerToUpdate = customerList.get(0);
        customerToUpdate.setFirstName("Wiktor");
        customerToUpdate.setEmail("tested@gmail.com");
        //When
        customerDbService.updateCustomer(customerToUpdate);
        Customer updatedCustomer = customerDbService.updateCustomer(customerToUpdate);
        customerDbService.deleteCustomer(updatedCustomer.getId());
        //Then
        assertEquals(customerToUpdate.getId(), updatedCustomer.getId());
        assertEquals("Wiktor", updatedCustomer.getFirstName());
        assertEquals("tested@gmail.com", updatedCustomer.getEmail());
        assertThrows(MyEntityNotFoundException.class, () -> customerDbService.getCustomer(updatedCustomer.getId()));
        System.out.println(updatedCustomer.getId());
    }

    @Test
    public void testThrowsMyEntityNotFoundException() throws MyEntityNotFoundException {
        //Given & When & Then
        MyEntityNotFoundException exception = assertThrows(MyEntityNotFoundException.class, () -> customerDbService.getCustomer(1L));
        assertEquals("Customer of id: 1", exception.getMessage());
        assertEquals(1L, exception.getRecordId());
    }
}