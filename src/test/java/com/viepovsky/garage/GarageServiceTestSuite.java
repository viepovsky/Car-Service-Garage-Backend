package com.viepovsky.garage;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Garage Db Service Test Suite")
class GarageServiceTestSuite {

    @InjectMocks
    private GarageService service;

    @Mock
    private GarageRepository repository;

    @Test
    void testGetAllGarages() {
        //Given
        List<Garage> garageList = new ArrayList<>();
        Garage mockedGarage = Mockito.mock(Garage.class);
        garageList.add(mockedGarage);
        when(repository.findAll()).thenReturn(garageList);
        //When
        List<Garage> retrievedGarageList = service.getAllGarages();
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
        when(repository.findAll()).thenReturn(garageList);
        //When
        List<String> retrievedCities = service.getAllGarageCities();
        //Then
        assertEquals(2, retrievedCities.size());
        assertEquals("Gdańsk", retrievedCities.get(0));
    }

    @Test
    void testSaveGarage() {
        //Given
        Garage mockedGarage = Mockito.mock(Garage.class);
        when(repository.save(mockedGarage)).thenReturn(mockedGarage);
        //When
        repository.save(mockedGarage);
        //Then
        verify(repository, times(1)).save(mockedGarage);
    }

    @Test
    void testDeleteGarage() throws MyEntityNotFoundException {
        //Given
        Garage mockedGarage = Mockito.mock(Garage.class);
        when(repository.findById(1L)).thenReturn(Optional.of(mockedGarage));
        doNothing().when(repository).deleteById(1L);
        //When
        service.deleteGarage(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Garage", 1L));
        verify(repository, times(1)).deleteById(1L);
    }
}