package com.viepovsky.car;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarFacadeTest {
    @InjectMocks
    private CarFacade facade;

    @Mock
    private CarService service;

    @Mock
    private CarMapper mapper;

    @Test
    void shouldGetCarsForGivenUsername() {
        //Given
        List<Car> mockedCarList = List.of(Mockito.mock(Car.class));
        List<CarDto> mockedCarDtoList = List.of(Mockito.mock(CarDto.class));
        when(service.getAllCarsForGivenUsername("username")).thenReturn(mockedCarList);
        when(mapper.mapToCarDtoList(mockedCarList)).thenReturn(mockedCarDtoList);
        //When
        List<CarDto> retrievedList = facade.getCarsForGivenUsername("username");
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldCreateCar() {
        //Given
        CarDto mockedCarDto = Mockito.mock(CarDto.class);
        Car mockedCar = Mockito.mock(Car.class);
        when(mapper.mapToCar(mockedCarDto)).thenReturn(mockedCar);
        doNothing().when(service).saveCar(mockedCar, "username");
        //When
        facade.createCar(mockedCarDto, "username");
        //Then
        verify(service, times(1)).saveCar(mockedCar, "username");
    }

    @Test
    void shouldUpdateCar() {
        //Given
        CarDto mockedCarDto = Mockito.mock(CarDto.class);
        Car mockedCar = Mockito.mock(Car.class);
        when(mapper.mapToCar(mockedCarDto)).thenReturn(mockedCar);
        doNothing().when(service).updateCar(mockedCar);
        //When
        facade.updateCar(mockedCarDto);
        //Then
        verify(service, times(1)).updateCar(mockedCar);
    }

    @Test
    void shouldDeleteCar() {
        //Given
        doNothing().when(service).deleteCar(1L);
        //When
        facade.deleteCar(1L);
        //Then
        verify(service, times(1)).deleteCar(1L);
    }
}