package com.backend.domain;

import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.service.CustomerDbService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@DisplayName("Customer Entity Test Suite")
class CustomerTestSuite {

    @Autowired
    private CustomerDbService customerDbService;

    @Test
    public void testSaveAndRetrieveCustomer() throws MyEntityNotFoundException {
        //Given
        Customer customer = new Customer(1L, "Oskar", "Raj", "testmail@gmail.com", "+48756756756", LocalDate.now(), null, new ArrayList<>(), new ArrayList<>());
        customerDbService.saveCustomer(customer);
        //When
        Customer retrievedCustomer = customerDbService.getCustomer(1L);
        List<Customer> customerList = customerDbService.getAllCustomers();
        Customer retrievedCustomer2 = customerList.get(0);
        //Then
        assertEquals(1L, retrievedCustomer.getId());
        assertEquals(1L, retrievedCustomer2.getId());
        assertEquals("testmail@gmail.com", retrievedCustomer.getEmail());
        assertEquals("testmail@gmail.com", retrievedCustomer2.getEmail());
        assertDoesNotThrow(() -> customerDbService.getCustomer(1L));
    }

    @Test
    public void testUpdateAndDeleteCustomer() throws MyEntityNotFoundException {
        //Given
        Customer customer = new Customer(1L, "Mariusz", "Raj", "h2base@gmail.com", "+48756756756", LocalDate.now(), null, new ArrayList<>(), new ArrayList<>());
        Customer customerToUpdate = new Customer(1L, "Wiktor", "Raj", "tested@gmail.com", "+48756756756", LocalDate.now(), null, new ArrayList<>(), new ArrayList<>());
        customerDbService.saveCustomer(customer);
        //When
        Customer updatedCustomer = customerDbService.updateCustomer(customerToUpdate);
        customerDbService.deleteCustomer(updatedCustomer.getId());
        //Then
        assertEquals(1L, updatedCustomer.getId());
        assertEquals("Wiktor", updatedCustomer.getFirstName());
        assertEquals("tested@gmail.com", updatedCustomer.getEmail());
        assertThrows(MyEntityNotFoundException.class, () -> customerDbService.getCustomer(updatedCustomer.getId()));
    }

    @Test
    public void testThrowsMyEntityNotFoundException() throws MyEntityNotFoundException {
        //Given & When & Then
        MyEntityNotFoundException exception = assertThrows(MyEntityNotFoundException.class, () -> customerDbService.getCustomer(1L));
        assertEquals("Customer of id: 1", exception.getMessage());
        assertEquals(1L, exception.getRecordId());
    }
}