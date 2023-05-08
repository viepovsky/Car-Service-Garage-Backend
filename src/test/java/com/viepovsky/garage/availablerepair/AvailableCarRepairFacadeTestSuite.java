package com.viepovsky.garage.availablerepair;

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
class AvailableCarRepairFacadeTestSuite {
    @InjectMocks
    private AvailableCarRepairFacade availableCarRepairFacade;

    @Mock
    private AvailableCarRepairService availableCarRepairService;

    @Mock
    private AvailableCarRepairMapper availableCarRepairMapper;

    @Test
    void shouldGetAvailableCarServices() {
        //Given
        List<AvailableCarRepair> carServiceList = List.of(Mockito.mock(AvailableCarRepair.class));
        List<AvailableCarRepairDto> carServiceDtoList = List.of(Mockito.mock(AvailableCarRepairDto.class));
        when(availableCarRepairService.getAllAvailableCarService(1L)).thenReturn(carServiceList);
        when(availableCarRepairMapper.mapToAvailableCarServiceDtoList(carServiceList)).thenReturn(carServiceDtoList);
        //When
        List<AvailableCarRepairDto> retrievedList = availableCarRepairFacade.getAvailableCarServices(1L);
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldCreateAvailableCarService() throws MyEntityNotFoundException {
        //Given
        AvailableCarRepairDto serviceDto = Mockito.mock(AvailableCarRepairDto.class);
        AvailableCarRepair service = Mockito.mock(AvailableCarRepair.class);
        when(availableCarRepairMapper.mapToAvailableCarService(serviceDto)).thenReturn(service);
        doNothing().when(availableCarRepairService).saveAvailableCarService(service, 1L);
        //When
        availableCarRepairFacade.createAvailableCarService(serviceDto, 1L);
        //Then
        verify(availableCarRepairService, times(1)).saveAvailableCarService(service, 1L);
    }

    @Test
    void shouldDeleteAvailableCarService() throws MyEntityNotFoundException {
        //Given
        doNothing().when(availableCarRepairService).deleteAvailableCarService(1L);
        //When
        availableCarRepairFacade.deleteAvailableCarService(1L);
        //Then
        verify(availableCarRepairService, times(1)).deleteAvailableCarService(1L);
    }
}