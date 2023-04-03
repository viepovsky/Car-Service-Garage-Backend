package com.backend.facade;

import com.backend.domain.Car;
import com.backend.domain.dto.CarDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.CarMapper;
import com.backend.scheduler.ApplicationScheduler;
import com.backend.service.CarDbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@MockBean(ApplicationScheduler.class)
class CarFacadeTestSuite {
    @InjectMocks
    private CarFacade carFacade;

    @Mock
    private CarDbService carDbService;

    @Mock
    private CarMapper carMapper;

    @Test
    void shouldGetCarsForGivenUsername() throws MyEntityNotFoundException {
        //Given
        List<Car> mockedCarList = List.of(Mockito.mock(Car.class));
        List<CarDto> mockedCarDtoList = List.of(Mockito.mock(CarDto.class));
        when(carDbService.getAllCarsForGivenUsername("username")).thenReturn(mockedCarList);
        when(carMapper.mapToCarDtoList(mockedCarList)).thenReturn(mockedCarDtoList);
        //When
        List<CarDto> retrievedList = carFacade.getCarsForGivenUsername("username");
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldCreateCar() throws MyEntityNotFoundException {
        //Given
        CarDto mockedCarDto = Mockito.mock(CarDto.class);
        Car mockedCar = Mockito.mock(Car.class);
        when(carMapper.mapToCar(mockedCarDto)).thenReturn(mockedCar);
        doNothing().when(carDbService).saveCar(mockedCar, "username");
        //When
        carFacade.createCar(mockedCarDto, "username");
        //Then
        verify(carDbService, times(1)).saveCar(mockedCar, "username");
    }

    @Test
    void shouldUpdateCar() throws MyEntityNotFoundException {
        //Given
        CarDto mockedCarDto = Mockito.mock(CarDto.class);
        Car mockedCar = Mockito.mock(Car.class);
        when(carMapper.mapToCar(mockedCarDto)).thenReturn(mockedCar);
        doNothing().when(carDbService).updateCar(mockedCar);
        //When
        carFacade.updateCar(mockedCarDto);
        //Then
        verify(carDbService, times(1)).updateCar(mockedCar);
    }

    @Test
    void shouldDeleteCar() throws MyEntityNotFoundException {
        //Given
        doNothing().when(carDbService).deleteCar(1L);
        //When
        carFacade.deleteCar(1L);
        //Then
        verify(carDbService, times(1)).deleteCar(1L);
    }
}