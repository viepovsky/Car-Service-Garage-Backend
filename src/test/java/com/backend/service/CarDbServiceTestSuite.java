package com.backend.service;

import com.backend.domain.Car;
import com.backend.domain.User;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.CarRepository;
import com.backend.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Car Db Service Test Suite")
class CarDbServiceTestSuite {

    @InjectMocks
    private CarDbService carDbService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void testGetAllCarsForGivenUsername() throws MyEntityNotFoundException {
        //Given
        User mockedUser = Mockito.mock(User.class);
        Car car = new Car("BMW", "3 Series", "Sedan", 2010, "diesel");
        List<Car> carList = new ArrayList<>();
        carList.add(car);

        when(userRepository.findByUsername("username")).thenReturn(Optional.of(mockedUser));
        when(mockedUser.getId()).thenReturn(1L);
        when(carRepository.findCarsByUserId(1L)).thenReturn(carList);
        //When
        List<Car> retrievedCarList = carDbService.getAllCarsForGivenUsername("username");
        //Then
        assertEquals(1, retrievedCarList.size());
        assertEquals("Sedan", retrievedCarList.get(0).getType());
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));;
    }

    @Test
    void testSaveCar() throws MyEntityNotFoundException {
        //Given
        User mockedUser = Mockito.mock(User.class);
        Car mockedCar = Mockito.mock(Car.class);
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(mockedUser));
        when(userRepository.save(mockedUser)).thenReturn(mockedUser);
        //When
        carDbService.saveCar(mockedCar, "username");
        //Then
        verify(userRepository, times(1)).save(mockedUser);
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
    }

    @Test
    void testUpdateCar() throws MyEntityNotFoundException {
        //Given
        Car mockedCar = Mockito.mock(Car.class);
        Car mockedCarToUpdate = Mockito.mock(Car.class);

        when(mockedCar.getId()).thenReturn(1L);
        when(carRepository.findById(1L)).thenReturn(Optional.of(mockedCarToUpdate));
        when(carRepository.save(mockedCarToUpdate)).thenReturn(mockedCarToUpdate);
        //When
        carDbService.updateCar(mockedCar);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Car", 1L));
        verify(carRepository, times(1)).save(mockedCar);
    }

    @Test
    void testDeleteCar() throws MyEntityNotFoundException {
        //Given
        Car mockedCar = Mockito.mock(Car.class);
        when(carRepository.findById(1L)).thenReturn(Optional.of(mockedCar));
        doNothing().when(carRepository).deleteById(1L);
        //When
        carDbService.deleteCar(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Car", 1L));
        verify(carRepository, times(1)).deleteById(1L);
    }
}