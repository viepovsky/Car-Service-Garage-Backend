package com.viepovsky.car_repair;

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
class CarRepairFacadeTest {
    @InjectMocks
    private CarRepairFacade facade;

    @Mock
    private CarRepairService service;

    @Mock
    private CarRepairMapper mapper;

    @Test
    void shouldGetCarServices() {
        //Given
        List<CarRepair> mockedCarRepairList = List.of(Mockito.mock(CarRepair.class));
        List<CarRepairDto> mockedCarRepairDtoList = List.of(Mockito.mock(CarRepairDto.class));
        when(service.getCarRepairs("username")).thenReturn(mockedCarRepairList);
        when(mapper.mapToCarServiceDtoList(mockedCarRepairList)).thenReturn(mockedCarRepairDtoList);
        //When
        List<CarRepairDto> retrievedList = facade.getCarRepairs("username");
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldDeleteCarService() {
        //Given
        doNothing().when(service).deleteCarRepair(1L);
        //When
        facade.deleteCarRepair(1L);
        //Then
        verify(service, times(1)).deleteCarRepair(1L);
    }

}