package com.backend.facade;

import com.backend.config.AdminConfig;
import com.backend.domain.AvailableCarService;
import com.backend.domain.dto.AvailableCarServiceDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.AvailableCarServiceMapper;
import com.backend.service.AvailableCarServiceDbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AvailableCarServiceFacadeTestSuite {
    @InjectMocks
    private AvailableCarServiceFacade availableCarServiceFacade;

    @Mock
    private AvailableCarServiceDbService availableCarServiceDbService;

    @Mock
    private AvailableCarServiceMapper availableCarServiceMapper;

    @Mock
    private AdminConfig adminConfig;

    @Value("${admin.api.key}")
    private String adminApiKey;

    @Test
    void shouldGetAvailableCarServices() {
        //Given
        List<AvailableCarService> carServiceList = List.of(Mockito.mock(AvailableCarService.class));
        List<AvailableCarServiceDto> carServiceDtoList = List.of(Mockito.mock(AvailableCarServiceDto.class));
        when(availableCarServiceDbService.getAllAvailableCarService(1L)).thenReturn(carServiceList);
        when(availableCarServiceMapper.mapToAvailableCarServiceDtoList(carServiceList)).thenReturn(carServiceDtoList);
        //When
        List<AvailableCarServiceDto> retrievedList = availableCarServiceFacade.getAvailableCarServices(1L);
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldCreateAvailableCarService() throws MyEntityNotFoundException {
        //Given
        AvailableCarServiceDto serviceDto = Mockito.mock(AvailableCarServiceDto.class);
        AvailableCarService service = Mockito.mock(AvailableCarService.class);
        when(adminConfig.getAdminApiKey()).thenReturn(adminApiKey);
        when(availableCarServiceMapper.mapToAvailableCarService(serviceDto)).thenReturn(service);
        doNothing().when(availableCarServiceDbService).saveAvailableCarService(service, 1L);
        //When
        ResponseEntity<String> response = availableCarServiceFacade.createAvailableCarService(serviceDto, 1L, adminApiKey);
        //Then
        verify(availableCarServiceDbService, times(1)).saveAvailableCarService(service, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldNotCreateAvailableCarService() throws MyEntityNotFoundException {
        //Given
        AvailableCarServiceDto serviceDto = Mockito.mock(AvailableCarServiceDto.class);
        AvailableCarService service = Mockito.mock(AvailableCarService.class);
        when(adminConfig.getAdminApiKey()).thenReturn(adminApiKey);
        //When
        ResponseEntity<String> response = availableCarServiceFacade.createAvailableCarService(serviceDto, 1L, "562");
        //Then
        verify(availableCarServiceDbService, times(0)).saveAvailableCarService(service, 1L);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void shouldDeleteAvailableCarService() throws MyEntityNotFoundException {
        //Given
        when(adminConfig.getAdminApiKey()).thenReturn(adminApiKey);
        doNothing().when(availableCarServiceDbService).deleteAvailableCarService(1L);
        //When
        ResponseEntity<String> response = availableCarServiceFacade.deleteAvailableCarService(1L, adminApiKey);
        //Then
        verify(availableCarServiceDbService, times(1)).deleteAvailableCarService(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldNotDeleteAvailableCarService() throws MyEntityNotFoundException {
        //Given
        when(adminConfig.getAdminApiKey()).thenReturn(adminApiKey);
        //When
        ResponseEntity<String> response = availableCarServiceFacade.deleteAvailableCarService(1L, "55ss");
        //Then
        verify(availableCarServiceDbService, times(0)).deleteAvailableCarService(1L);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}