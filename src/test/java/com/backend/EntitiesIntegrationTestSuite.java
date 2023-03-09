package com.backend;

import com.backend.domain.*;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@DisplayName("Entities Integration Test Suites")
public class EntitiesIntegrationTestSuite {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private GarageRepository garageRepository;

    @Autowired
    private CarServiceRepository carServiceRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    public void testBookingServiceForNewCustomer() throws MyEntityNotFoundException {
        //Given
        Customer customer = new Customer(1L, "Oskar", "Raj", "testmail@gmail.com", "+48756756756", LocalDateTime.now(), null, new ArrayList<>(), new ArrayList<>());
        customerRepository.save(customer);
        customer = customerRepository.findAll().get(0);

        User user = new User(1L, "viepovsky", "password123");
        user.setCustomer(customer);
        customer.setUser(user);
        customerRepository.save(customer);
        customer = customerRepository.findAll().get(0);

        Car car1 = new Car(1L, "BMW", "3", "Sedan", 2020, "diesel");
        Car car2 = new Car(2L, "BMW", "5","Sedan", 2005, "diesel");
        car1.setCustomer(customer);
        car2.setCustomer(customer);
        customer.getCarList().add(car1);
        customer.getCarList().add(car2);
        customerRepository.save(customer);
        customer = customerRepository.findAll().get(0);
        car1 = carRepository.findAll().get(0);

        Garage garage = new Garage(1L, "Speed Garage", "Garage description", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        garageRepository.save(garage);
        Garage garageToUpdate = garageRepository.findAll().get(0);

        GarageWorkTime garageWorkTime1 = new GarageWorkTime(1L, WorkDays.MONDAY, LocalTime.of(8, 0), LocalTime.of(16, 0), garageToUpdate);
        GarageWorkTime garageWorkTime2 = new GarageWorkTime(2L, WorkDays.TUESDAY, LocalTime.of(8, 0), LocalTime.of(16, 0), garageToUpdate);
        garageToUpdate.getGarageWorkTimeList().add(garageWorkTime1);
        garageToUpdate.getGarageWorkTimeList().add(garageWorkTime2);
        AvailableCarService availableCarService1 = new AvailableCarService(1L, "Oil change", "Description of oil change", BigDecimal.valueOf(50), 30, "BMW, AUDI", BigDecimal.valueOf(1.2), garageToUpdate);
        AvailableCarService availableCarService2 = new AvailableCarService(2L, "Suspension check", "Description of suspension check", BigDecimal.valueOf(30), 10, "BMW, AUDI", BigDecimal.valueOf(1.2), garageToUpdate);
        garageToUpdate.getAvailableCarServiceList().add(availableCarService1);
        garageToUpdate.getAvailableCarServiceList().add(availableCarService2);
        garageRepository.save(garageToUpdate);
        garage = garageRepository.findAll().get(0);

        CarService carService = new CarService(1L, availableCarService1.getName(), availableCarService1.getDescription(), availableCarService1.getCost(), availableCarService1.getRepairTimeInMinutes(), null, null, null, null);
        carService.setCar(car1);
        carService.setCustomer(customer);
        carServiceRepository.save(carService);
        carService = carServiceRepository.findAll().get(0);

        Booking booking = new Booking(1L, BookingStatus.WAITING_FOR_CUSTOMER, LocalDate.of(2023,3,20),LocalTime.of(8,0),LocalTime.of(8,30), LocalDateTime.now(),BigDecimal.valueOf(0), new ArrayList<>(), null);
        booking.getCarServiceList().add(carService);
        booking.setGarage(garage);
        booking.setTotalCost(carService.getCost());
        bookingRepository.save(booking);
        booking = bookingRepository.findAll().get(0);

        carService.setBooking(booking);
        carServiceRepository.save(carService);
        carService = carServiceRepository.findAll().get(0);

        customer.getServicesList().add(carService);
        customerRepository.save(customer);

        car1.getCarServicesList().add(carService);
        carRepository.save(car1);

        garage.getBookingList().add(booking);
        garageRepository.save(garage);

        //When
        Customer retrievedCustomer = customerRepository.findAll().get(0);
        CarService retrievedCarService = carServiceRepository.findAll().get(0);
        Garage retrievedGarage = garageRepository.findAll().get(0);
        Booking retrievedBooking = bookingRepository.findAll().get(0);

        //Then
        assertEquals(customer.getFirstName(), retrievedCustomer.getFirstName());
        assertEquals("BMW", retrievedCustomer.getCarList().get(0).getMake());
        assertEquals(30, retrievedCustomer.getServicesList().get(0).getRepairTimeInMinutes());
        assertEquals(30, retrievedCustomer.getCarList().get(0).getCarServicesList().get(0).getRepairTimeInMinutes());
        assertEquals(LocalTime.of(8,30), retrievedCustomer.getCarList().get(0).getCarServicesList().get(0).getBooking().getEndHour());
        assertEquals("Speed Garage", retrievedCustomer.getCarList().get(0).getCarServicesList().get(0).getBooking().getGarage().getName());
        assertEquals(carService.getName(), retrievedCarService.getName());
        assertEquals(carService.getBooking(), retrievedCarService.getBooking());
        assertEquals(carService.getBooking().getGarage(), retrievedCarService.getBooking().getGarage());
        assertEquals("Speed Garage", retrievedGarage.getName());
        assertEquals(BookingStatus.WAITING_FOR_CUSTOMER, retrievedGarage.getBookingList().get(0).getStatus());
        assertEquals(30, retrievedGarage.getBookingList().get(0).getCarServiceList().get(0).getRepairTimeInMinutes());
        assertEquals(BookingStatus.WAITING_FOR_CUSTOMER, retrievedBooking.getStatus());
        assertEquals(30, retrievedBooking.getCarServiceList().get(0).getRepairTimeInMinutes());
    }
}
