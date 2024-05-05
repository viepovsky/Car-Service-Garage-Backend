package com.viepovsky.vehicle;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.viepovsky.user.UserService;
import com.viepovsky.user.model.AppUser;
import com.viepovsky.utility.exceptions.MyEntityNotFoundException;
import com.viepovsky.vehicle.model.Model;
import com.viepovsky.vehicle.model.Vehicle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@DisplayName("Car Db Service Tests")
class CarServiceTest {

    @InjectMocks
    private VehicleService carService;

    @Mock
    private VehicleRepository carRepository;

    @Mock
    private UserService userService;

    @Test
    void testGetAllCarsForGivenUsername() {
        //Given
        AppUser mockedUser = Mockito.mock(AppUser.class);
        Model model = Mockito.mock(Model.class);
        Vehicle car = new Vehicle(model, 2010);
        List<Vehicle> carList = new ArrayList<>();
        carList.add(car);

        when(userService.getUser(anyString())).thenReturn(mockedUser);
        when(mockedUser.getId()).thenReturn(1L);
        when(carRepository.findVehiclesByUserId(1L)).thenReturn(carList);
        //When
        List<Vehicle> retrievedCarList = carService.getVehiclesByUsername("username");
        //Then
        assertEquals(1, retrievedCarList.size());
//        assertEquals("Sedan", retrievedCarList.get(0).getType()); TODO fix it
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
        ;
    }

    @Test
    void testSaveCar() {
        //Given
        AppUser mockedUser = Mockito.mock(AppUser.class);
        Vehicle mockedCar = Mockito.mock(Vehicle.class);
        when(userService.getUser(anyString())).thenReturn(mockedUser);
        when(userService.saveUser(any(AppUser.class))).thenReturn(Mockito.mock(AppUser.class));
        //When
        carService.saveVehicle(mockedCar, "username", vehicleDto.modelId());
        //Then
        verify(userService, times(1)).saveUser(any(AppUser.class));
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Username: " + "username"));
    }

    @Test
    void testUpdateCar() {
        //TODO fixit
//        //Given
//        Vehicle car = new Vehicle(1L, "BMW", "3 Series", 2010, "Sedan", "diesel", null, new ArrayList<>());
//
//        Vehicle carToUpdate = new Vehicle(1L, "BMW", "3 Series", 2010, "Sedan", "diesel", new AppUser(), new ArrayList<>());
//
//        when(carRepository.findById(1L)).thenReturn(Optional.of(carToUpdate));
//        when(carRepository.save(car)).thenReturn(car);
//        //When
//        carService.updateCar(car);
//        //Then
//        assertDoesNotThrow(() -> new MyEntityNotFoundException("Car", 1L));
//        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testDeleteCar() {
        //Given
        when(carRepository.existsById(1L)).thenReturn(true);
        doNothing().when(carRepository).deleteById(1L);
        //When
        carService.deleteVehicle(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Car", 1L));
        verify(carRepository, times(1)).deleteById(1L);
    }
}