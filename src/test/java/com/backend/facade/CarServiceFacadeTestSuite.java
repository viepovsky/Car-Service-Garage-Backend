package com.backend.facade;

import com.backend.domain.CarService;
import com.backend.domain.dto.CarServiceDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.CarServiceMapper;
import com.backend.scheduler.ApplicationScheduler;
import com.backend.service.CarServiceDbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@MockBean(ApplicationScheduler.class)
class CarServiceFacadeTestSuite {
    @InjectMocks
    private CarServiceFacade carServiceFacade;

    @Mock
    private CarServiceDbService carServiceDbService;

    @Mock
    private CarServiceMapper carServiceMapper;

    @Test
    void shouldGetCarServices() throws MyEntityNotFoundException {
        //Given
        List<CarService> mockedCarServiceList = List.of(Mockito.mock(CarService.class));
        List<CarServiceDto> mockedCarServiceDtoList = List.of(Mockito.mock(CarServiceDto.class));
        when(carServiceDbService.getCarServices("username")).thenReturn(mockedCarServiceList);
        when(carServiceMapper.mapToCarServiceDtoList(mockedCarServiceList)).thenReturn(mockedCarServiceDtoList);
        //When
        List<CarServiceDto> retrievedList = carServiceFacade.getCarServices("username");
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldDeleteCarService() throws MyEntityNotFoundException {
        //Given
        doNothing().when(carServiceDbService).deleteCarService(1L);
        //When
        carServiceFacade.deleteCarService(1L);
        //Then
        verify(carServiceDbService, times(1)).deleteCarService(1L);
    }

}