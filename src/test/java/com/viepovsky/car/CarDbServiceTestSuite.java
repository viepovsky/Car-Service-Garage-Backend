package com.viepovsky.car;

import com.viepovsky.car.Car;
import com.viepovsky.car.CarDbService;
import com.viepovsky.user.User;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.car.CarRepository;
import com.viepovsky.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
        Car car = new Car(1L, "BMW", "3 Series", 2010, "Sedan", "diesel", null, new ArrayList<>());

        Car carToUpdate = new Car(1L, "BMW", "3 Series", 2010, "Sedan", "diesel", new User(), new ArrayList<>());

        when(carRepository.findById(1L)).thenReturn(Optional.of(carToUpdate));
        when(carRepository.save(car)).thenReturn(car);
        //When
        carDbService.updateCar(car);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Car", 1L));
        verify(carRepository, times(1)).save(car);
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