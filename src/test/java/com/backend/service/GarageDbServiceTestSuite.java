package com.backend.service;

import com.backend.domain.Garage;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.GarageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Garage Db Service Test Suite")
class GarageDbServiceTestSuite {

    @InjectMocks
    private GarageDbService garageDbService;

    @Mock
    private GarageRepository garageRepository;

    @Test
    void testGetAllGarages() {
        //Given
        List<Garage> garageList = new ArrayList<>();
        Garage mockedGarage = Mockito.mock(Garage.class);
        garageList.add(mockedGarage);
        when(garageRepository.findAll()).thenReturn(garageList);
        //When
        List<Garage> retrievedGarageList = garageDbService.getAllGarages();
        //Then
        assertEquals(1, retrievedGarageList.size());
    }

    @Test
    void testAllGarageCities() {
        //Given
        List<Garage> garageList = new ArrayList<>();
        Garage mockedGarage = Mockito.mock(Garage.class);
        Garage mockedGarage2 = Mockito.mock(Garage.class);
        garageList.add(mockedGarage);
        garageList.add(mockedGarage2);
        when(mockedGarage.getAddress()).thenReturn("Gdańsk 62-500, test st");
        when(mockedGarage2.getAddress()).thenReturn("Łódź 62-500, test st");
        when(garageRepository.findAll()).thenReturn(garageList);
        //When
        List<String> retrievedCities = garageDbService.getAllGarageCities();
        //Then
        assertEquals(2, retrievedCities.size());
        assertEquals("Gdańsk", retrievedCities.get(0));
    }

    @Test
    void testSaveGarage() {
        //Given
        Garage mockedGarage = Mockito.mock(Garage.class);
        when(garageRepository.save(mockedGarage)).thenReturn(mockedGarage);
        //When
        garageRepository.save(mockedGarage);
        //Then
        verify(garageRepository, times(1)).save(mockedGarage);
    }

    @Test
    void testDeleteGarage() throws MyEntityNotFoundException {
        //Given
        Garage mockedGarage = Mockito.mock(Garage.class);
        when(garageRepository.findById(1L)).thenReturn(Optional.of(mockedGarage));
        doNothing().when(garageRepository).deleteById(1L);
        //When
        garageDbService.deleteGarage(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Garage", 1L));
        verify(garageRepository, times(1)).deleteById(1L);
    }
}