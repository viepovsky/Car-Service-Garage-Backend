package com.viepovsky.car;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.user.User;
import com.viepovsky.user.UserService;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Car Db Service Tests")
class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserService userService;

    @Test
    void testGetAllCarsForGivenUsername() {
        //Given
        User mockedUser = Mockito.mock(User.class);
        Car car = new Car("BMW", "3 Series", "Sedan", 2010, "diesel");
        List<Car> carList = new ArrayList<>();
        carList.add(car);

        when(userService.getUser(anyString())).thenReturn(mockedUser);
        when(mockedUser.getId()).thenReturn(1L);
        when(carRepository.findCarsByUserId(1L)).thenReturn(carList);
        //When
        List<Car> retrievedCarList = carService.getAllCarsForGivenUsername("username");
        //Then
        assertEquals(1, retrievedCarList.size());
        assertEquals("Sedan", retrievedCarList.get(0).getType());
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
        ;
    }

    @Test
    void testSaveCar() {
        //Given
        User mockedUser = Mockito.mock(User.class);
        Car mockedCar = Mockito.mock(Car.class);
        when(userService.getUser(anyString())).thenReturn(mockedUser);
        when(userService.saveUser(any(User.class))).thenReturn(Mockito.mock(User.class));
        //When
        carService.saveCar(mockedCar, "username");
        //Then
        verify(userService, times(1)).saveUser(any(User.class));
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
    }

    @Test
    void testUpdateCar() {
        //Given
        Car car = new Car(1L, "BMW", "3 Series", 2010, "Sedan", "diesel", null, new ArrayList<>());

        Car carToUpdate = new Car(1L, "BMW", "3 Series", 2010, "Sedan", "diesel", new User(), new ArrayList<>());

        when(carRepository.findById(1L)).thenReturn(Optional.of(carToUpdate));
        when(carRepository.save(car)).thenReturn(car);
        //When
        carService.updateCar(car);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Car", 1L));
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testDeleteCar() {
        //Given
        when(carRepository.existsById(1L)).thenReturn(true);
        doNothing().when(carRepository).deleteById(1L);
        //When
        carService.deleteCar(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Car", 1L));
        verify(carRepository, times(1)).deleteById(1L);
    }
}