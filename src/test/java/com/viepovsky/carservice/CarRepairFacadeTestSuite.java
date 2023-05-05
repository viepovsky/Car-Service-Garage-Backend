package com.viepovsky.carservice;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.scheduler.ApplicationScheduler;
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
class CarRepairFacadeTestSuite {
    @InjectMocks
    private CarRepairFacade carRepairFacade;

    @Mock
    private CarRepairService carRepairService;

    @Mock
    private CarRepairMapper carRepairMapper;

    @Test
    void shouldGetCarServices() throws MyEntityNotFoundException {
        //Given
        List<CarRepair> mockedCarRepairList = List.of(Mockito.mock(CarRepair.class));
        List<CarRepairDto> mockedCarRepairDtoList = List.of(Mockito.mock(CarRepairDto.class));
        when(carRepairService.getCarServices("username")).thenReturn(mockedCarRepairList);
        when(carRepairMapper.mapToCarServiceDtoList(mockedCarRepairList)).thenReturn(mockedCarRepairDtoList);
        //When
        List<CarRepairDto> retrievedList = carRepairFacade.getCarServices("username");
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldDeleteCarService() throws MyEntityNotFoundException {
        //Given
        doNothing().when(carRepairService).deleteCarService(1L);
        //When
        carRepairFacade.deleteCarService(1L);
        //Then
        verify(carRepairService, times(1)).deleteCarService(1L);
    }

}