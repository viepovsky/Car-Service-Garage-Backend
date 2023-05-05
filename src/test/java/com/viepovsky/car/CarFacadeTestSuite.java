package com.viepovsky.car;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.scheduler.ApplicationScheduler;
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
    private CarService carService;

    @Mock
    private CarMapper carMapper;

    @Test
    void shouldGetCarsForGivenUsername() throws MyEntityNotFoundException {
        //Given
        List<Car> mockedCarList = List.of(Mockito.mock(Car.class));
        List<CarDto> mockedCarDtoList = List.of(Mockito.mock(CarDto.class));
        when(carService.getAllCarsForGivenUsername("username")).thenReturn(mockedCarList);
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
        doNothing().when(carService).saveCar(mockedCar, "username");
        //When
        carFacade.createCar(mockedCarDto, "username");
        //Then
        verify(carService, times(1)).saveCar(mockedCar, "username");
    }

    @Test
    void shouldUpdateCar() throws MyEntityNotFoundException {
        //Given
        CarDto mockedCarDto = Mockito.mock(CarDto.class);
        Car mockedCar = Mockito.mock(Car.class);
        when(carMapper.mapToCar(mockedCarDto)).thenReturn(mockedCar);
        doNothing().when(carService).updateCar(mockedCar);
        //When
        carFacade.updateCar(mockedCarDto);
        //Then
        verify(carService, times(1)).updateCar(mockedCar);
    }

    @Test
    void shouldDeleteCar() throws MyEntityNotFoundException {
        //Given
        doNothing().when(carService).deleteCar(1L);
        //When
        carFacade.deleteCar(1L);
        //Then
        verify(carService, times(1)).deleteCar(1L);
    }
}