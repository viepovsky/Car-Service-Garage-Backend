package com.viepovsky.garage.available_car_repair;

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
class AvailableCarRepairFacadeTest {
    @InjectMocks
    private AvailableCarRepairFacade facade;

    @Mock
    private AvailableCarRepairService service;

    @Mock
    private AvailableCarRepairMapper mapper;

    @Test
    void shouldGetAvailableCarServices() {
        //Given
        List<AvailableCarRepair> carServiceList = List.of(Mockito.mock(AvailableCarRepair.class));
        List<AvailableCarRepairDto> carServiceDtoList = List.of(Mockito.mock(AvailableCarRepairDto.class));
        when(service.getAllAvailableCarRepair(1L)).thenReturn(carServiceList);
        when(mapper.mapToAvailableCarServiceDtoList(carServiceList)).thenReturn(carServiceDtoList);
        //When
        List<AvailableCarRepairDto> retrievedList = facade.getAvailableCarServices(1L);
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldCreateAvailableCarService() {
        //Given
        AvailableCarRepairDto serviceDto = Mockito.mock(AvailableCarRepairDto.class);
        AvailableCarRepair service = Mockito.mock(AvailableCarRepair.class);
        when(mapper.mapToAvailableCarService(serviceDto)).thenReturn(service);
        doNothing().when(this.service).saveAvailableCarRepair(service, 1L);
        //When
        facade.createAvailableCarService(serviceDto, 1L);
        //Then
        verify(this.service, times(1)).saveAvailableCarRepair(service, 1L);
    }

    @Test
    void shouldDeleteAvailableCarService() {
        //Given
        doNothing().when(service).deleteAvailableCarRepair(1L);
        //When
        facade.deleteAvailableCarService(1L);
        //Then
        verify(service, times(1)).deleteAvailableCarRepair(1L);
    }
}