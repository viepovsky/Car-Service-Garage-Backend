package com.backend.facade;

import com.backend.config.AdminConfig;
import com.backend.domain.GarageWorkTime;
import com.backend.domain.dto.GarageWorkTimeDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.GarageWorkTimeMapper;
import com.backend.scheduler.ApplicationScheduler;
import com.backend.service.GarageWorkTimeDbService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@MockBean(ApplicationScheduler.class)
class GarageWorkTimeFacadeTestSuite {
    @InjectMocks
    private GarageWorkTimeFacade garageWorkTimeFacade;

    @Mock
    private GarageWorkTimeDbService garageWorkTimeDbService;

    @Mock
    private GarageWorkTimeMapper garageWorkTimeMapper;

    @Mock
    private AdminConfig adminConfig;

    @Value("${admin.api.key}")
    private String adminApiKey;

    @Test
    void shouldCreateGarageWorkTime() throws MyEntityNotFoundException {
        //Given
        GarageWorkTimeDto mockedDto = Mockito.mock(GarageWorkTimeDto.class);
        GarageWorkTime mocked = Mockito.mock(GarageWorkTime.class);
        when(adminConfig.getAdminApiKey()).thenReturn(adminApiKey);
        when(garageWorkTimeMapper.mapToGarageWorkTime(mockedDto)).thenReturn(mocked);
        doNothing().when(garageWorkTimeDbService).saveGarageWorkTime(mocked, 1L);
        //When
        ResponseEntity<String> response = garageWorkTimeFacade.createGarageWorkTime(mockedDto, 1L, adminApiKey);
        //Then
        verify(garageWorkTimeDbService, times(1)).saveGarageWorkTime(mocked, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldNotCreateGarageWorkTime() throws MyEntityNotFoundException {
        //Given
        GarageWorkTimeDto mockedDto = Mockito.mock(GarageWorkTimeDto.class);
        GarageWorkTime mocked = Mockito.mock(GarageWorkTime.class);
        when(adminConfig.getAdminApiKey()).thenReturn(adminApiKey);
        when(garageWorkTimeMapper.mapToGarageWorkTime(mockedDto)).thenReturn(mocked);
        doNothing().when(garageWorkTimeDbService).saveGarageWorkTime(mocked, 1L);
        //When
        ResponseEntity<String> response = garageWorkTimeFacade.createGarageWorkTime(mockedDto, 1L, "adminApiKey");
        //Then
        verify(garageWorkTimeDbService, times(0)).saveGarageWorkTime(mocked, 1L);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void shouldDeleteGarageWorkTime() throws MyEntityNotFoundException {
        //Given
        when(adminConfig.getAdminApiKey()).thenReturn(adminApiKey);
        doNothing().when(garageWorkTimeDbService).deleteGarageWorkTime(1L);
        //When
        ResponseEntity<String> response = garageWorkTimeFacade.deleteGarageWorkTime(1L, adminApiKey);
        //Then
        verify(garageWorkTimeDbService, times(1)).deleteGarageWorkTime(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldNotDeleteGarageWorkTime() throws MyEntityNotFoundException {
        //Given
        when(adminConfig.getAdminApiKey()).thenReturn(adminApiKey);
        //When
        ResponseEntity<String> response = garageWorkTimeFacade.deleteGarageWorkTime(1L, "adminApiKey");
        //Then
        verify(garageWorkTimeDbService, times(0)).deleteGarageWorkTime(1L);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}