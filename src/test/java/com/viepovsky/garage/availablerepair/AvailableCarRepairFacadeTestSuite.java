package com.viepovsky.garage.availablerepair;

import com.viepovsky.config.AdminConfig;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.scheduler.ApplicationScheduler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

    @Mock
    private AdminConfig adminConfig;

    @Value("${admin.api.key}")
    private String adminApiKey;

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
        when(adminConfig.getAdminApiKey()).thenReturn(adminApiKey);
        when(availableCarRepairMapper.mapToAvailableCarService(serviceDto)).thenReturn(service);
        doNothing().when(availableCarRepairService).saveAvailableCarService(service, 1L);
        //When
        ResponseEntity<String> response = availableCarRepairFacade.createAvailableCarService(serviceDto, 1L, adminApiKey);
        //Then
        verify(availableCarRepairService, times(1)).saveAvailableCarService(service, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldNotCreateAvailableCarService() throws MyEntityNotFoundException {
        //Given
        AvailableCarRepairDto serviceDto = Mockito.mock(AvailableCarRepairDto.class);
        AvailableCarRepair service = Mockito.mock(AvailableCarRepair.class);
        when(adminConfig.getAdminApiKey()).thenReturn(adminApiKey);
        //When
        ResponseEntity<String> response = availableCarRepairFacade.createAvailableCarService(serviceDto, 1L, "562");
        //Then
        verify(availableCarRepairService, times(0)).saveAvailableCarService(service, 1L);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void shouldDeleteAvailableCarService() throws MyEntityNotFoundException {
        //Given
        when(adminConfig.getAdminApiKey()).thenReturn(adminApiKey);
        doNothing().when(availableCarRepairService).deleteAvailableCarService(1L);
        //When
        ResponseEntity<String> response = availableCarRepairFacade.deleteAvailableCarService(1L, adminApiKey);
        //Then
        verify(availableCarRepairService, times(1)).deleteAvailableCarService(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldNotDeleteAvailableCarService() throws MyEntityNotFoundException {
        //Given
        when(adminConfig.getAdminApiKey()).thenReturn(adminApiKey);
        //When
        ResponseEntity<String> response = availableCarRepairFacade.deleteAvailableCarService(1L, "55ss");
        //Then
        verify(availableCarRepairService, times(0)).deleteAvailableCarService(1L);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}