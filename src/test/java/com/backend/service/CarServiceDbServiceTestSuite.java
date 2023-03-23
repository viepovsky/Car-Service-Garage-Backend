package com.backend.service;

import com.backend.domain.CarService;
import com.backend.domain.User;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@DisplayName("Car Service Db Service Test Suite")
class CarServiceDbServiceTestSuite {
    @InjectMocks
    private CarServiceDbService carServiceDbService;

    @Mock
    private CarServiceRepository carServiceRepository;

    @Mock
    private AvailableCarServiceRepository availableCarServiceRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Test
    void testGetCarServices() throws MyEntityNotFoundException {
        //Given
        List<CarService> carServiceList = new ArrayList<>();
        CarService mockedCarService = Mockito.mock(CarService.class);
        carServiceList.add(mockedCarService);
        User mockedUser = Mockito.mock(User.class);
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(mockedUser));
        when(mockedUser.getId()).thenReturn(1L);
        when(carServiceRepository.findCarServicesByUserId(1L)).thenReturn(carServiceList);
        //When
        List<CarService> retrievedCarServiceList = carServiceDbService.getCarServices("username");
        //Then
        assertEquals(1, retrievedCarServiceList.size());
    }

    @Test
    void testSaveCarService() {

    }
}