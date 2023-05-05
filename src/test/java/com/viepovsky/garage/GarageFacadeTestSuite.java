package com.viepovsky.garage;

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
class GarageFacadeTestSuite {
    @InjectMocks
    private GarageFacade garageFacade;

    @Mock
    private GarageService garageService;

    @Mock
    private GarageMapper garageMapper;

    @Mock
    private AdminConfig adminConfig;

    @Value("${admin.api.key}")
    private String adminKey;

    @Test
    void shouldGetAllGarages() {
        //Given
        List<Garage> mockedGarageList = List.of(Mockito.mock(Garage.class));
        List<GarageDto> mockedGarageDtoList = List.of(Mockito.mock(GarageDto.class));
        when(garageService.getAllGarages()).thenReturn(mockedGarageList);
        when(garageMapper.mapToGarageDtoList(mockedGarageList)).thenReturn(mockedGarageDtoList);
        //When
        List<GarageDto> retrievedList = garageFacade.getAllGarages();
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldCreateGarage() {
        //Given
        GarageDto mockedGarageDto = Mockito.mock(GarageDto.class);
        Garage mockedGarage = Mockito.mock(Garage.class);
        when(adminConfig.getAdminApiKey()).thenReturn(adminKey);
        when(garageMapper.mapToGarage(mockedGarageDto)).thenReturn(mockedGarage);
        doNothing().when(garageService).saveGarage(mockedGarage);
        //When
        ResponseEntity<String> response = garageFacade.createGarage(mockedGarageDto, adminKey);
        //Then
        verify(garageService, times(1)).saveGarage(mockedGarage);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldNotCreateGarage() {
        //Given
        GarageDto mockedGarageDto = Mockito.mock(GarageDto.class);
        Garage mockedGarage = Mockito.mock(Garage.class);
        when(adminConfig.getAdminApiKey()).thenReturn(adminKey);
        when(garageMapper.mapToGarage(mockedGarageDto)).thenReturn(mockedGarage);
        doNothing().when(garageService).saveGarage(mockedGarage);
        //When
        ResponseEntity<String> response = garageFacade.createGarage(mockedGarageDto, "2523ddd");
        //Then
        verify(garageService, times(0)).saveGarage(mockedGarage);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void shouldDeleteGarage() throws MyEntityNotFoundException {
        //Given
        when(adminConfig.getAdminApiKey()).thenReturn(adminKey);
        doNothing().when(garageService).deleteGarage(1L);
        //When
        ResponseEntity<String> response = garageFacade.deleteGarage(1L, adminKey);
        //Then
        verify(garageService, times(1)).deleteGarage(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldNotDeleteGarage() throws MyEntityNotFoundException {
        //Given
        when(adminConfig.getAdminApiKey()).thenReturn(adminKey);
        doNothing().when(garageService).deleteGarage(1L);
        //When
        ResponseEntity<String> response = garageFacade.deleteGarage(1L, "awdawdad");
        //Then
        verify(garageService, times(0)).deleteGarage(1L);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}