package com.viepovsky.garage;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
        Garage garage = new Garage();
        garageList.add(garage);
        when(repository.findAll()).thenReturn(garageList);
        //When
        List<Garage> retrievedGarageList = service.getAllGarages();
        //Then
        assertEquals(1, retrievedGarageList.size());
    }

    @Test
    void testGetGarage() {
        //Given
        var garage = new Garage();
        when(repository.findById(anyLong())).thenReturn(Optional.of(garage));
        //When
        var retrievedGarage = service.getGarage(5L);
        //Then
        assertNotNull(retrievedGarage);
    }

    @Test
    void testAllGarageCities() {
        //Given
        List<Garage> garageList = new ArrayList<>();
        var garage = new Garage();
        var garage2 = new Garage();
        garage.setAddress("Gdańsk 62-500, test st");
        garage2.setAddress("Łódź 62-500, test st");
        garageList.add(garage);
        garageList.add(garage2);
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
        var garage = new Garage();
        when(repository.save(garage)).thenReturn(garage);
        //When
        service.saveGarage(garage);
        //Then
        verify(repository, times(1)).save(garage);
    }

    @Test
    void testDeleteGarage() throws MyEntityNotFoundException {
        //Given
        var garage = new Garage();
        when(repository.findById(anyLong())).thenReturn(Optional.of(garage));
        doNothing().when(repository).deleteById(anyLong());
        //When
        service.deleteGarage(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Garage", 1L));
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void testDeleteGarageShouldThrowExceptionIfGarageDoesNotExist() throws MyEntityNotFoundException {
        //Given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        //When & then
        assertThrows(MyEntityNotFoundException.class, () -> service.deleteGarage(1L));
    }
}