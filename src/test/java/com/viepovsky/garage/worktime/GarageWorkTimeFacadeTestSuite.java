package com.viepovsky.garage.worktime;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@MockBean(ApplicationScheduler.class)
class GarageWorkTimeFacadeTestSuite {
    @InjectMocks
    private GarageWorkTimeFacade garageWorkTimeFacade;

    @Mock
    private GarageWorkTimeService garageWorkTimeService;

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
        doNothing().when(garageWorkTimeService).saveGarageWorkTime(mocked, 1L);
        //When
        ResponseEntity<String> response = garageWorkTimeFacade.createGarageWorkTime(mockedDto, 1L, adminApiKey);
        //Then
        verify(garageWorkTimeService, times(1)).saveGarageWorkTime(mocked, 1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldNotCreateGarageWorkTime() throws MyEntityNotFoundException {
        //Given
        GarageWorkTimeDto mockedDto = Mockito.mock(GarageWorkTimeDto.class);
        GarageWorkTime mocked = Mockito.mock(GarageWorkTime.class);
        when(adminConfig.getAdminApiKey()).thenReturn(adminApiKey);
        when(garageWorkTimeMapper.mapToGarageWorkTime(mockedDto)).thenReturn(mocked);
        doNothing().when(garageWorkTimeService).saveGarageWorkTime(mocked, 1L);
        //When
        ResponseEntity<String> response = garageWorkTimeFacade.createGarageWorkTime(mockedDto, 1L, "adminApiKey");
        //Then
        verify(garageWorkTimeService, times(0)).saveGarageWorkTime(mocked, 1L);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void shouldDeleteGarageWorkTime() throws MyEntityNotFoundException {
        //Given
        when(adminConfig.getAdminApiKey()).thenReturn(adminApiKey);
        doNothing().when(garageWorkTimeService).deleteGarageWorkTime(1L);
        //When
        ResponseEntity<String> response = garageWorkTimeFacade.deleteGarageWorkTime(1L, adminApiKey);
        //Then
        verify(garageWorkTimeService, times(1)).deleteGarageWorkTime(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldNotDeleteGarageWorkTime() throws MyEntityNotFoundException {
        //Given
        when(adminConfig.getAdminApiKey()).thenReturn(adminApiKey);
        //When
        ResponseEntity<String> response = garageWorkTimeFacade.deleteGarageWorkTime(1L, "adminApiKey");
        //Then
        verify(garageWorkTimeService, times(0)).deleteGarageWorkTime(1L);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}