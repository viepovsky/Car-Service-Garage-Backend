package com.backend.service;

import com.backend.domain.Garage;
import com.backend.domain.GarageWorkTime;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.GarageRepository;
import com.backend.repository.GarageWorkTimeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Garage Work Time Db Service Test Suite")
class GarageWorkTimeDbServiceTestSuite {

    @InjectMocks
    private GarageWorkTimeDbService garageWorkTimeDbService;

    @Mock
    private GarageWorkTimeRepository garageWorkTimeRepository;

    @Mock
    private GarageRepository garageRepository;

    @Test
    void testGetAllGarageWorkTimes() {
        //Given
        List<GarageWorkTime> garageWorkTimeList = new ArrayList<>();
        GarageWorkTime garageWorkTime = Mockito.mock(GarageWorkTime.class);
        garageWorkTimeList.add(garageWorkTime);
        when(garageWorkTimeRepository.findAll()).thenReturn(garageWorkTimeList);
        //When
        List<GarageWorkTime> retrievedGarageWorkTimeList = garageWorkTimeDbService.getAllGarageWorkTimes();
        //Then
        assertEquals(1, retrievedGarageWorkTimeList.size());
    }

    @Test
    void testSaveGarageWorkTime() throws MyEntityNotFoundException {
        //Given
        Garage mockedGarage = Mockito.mock(Garage.class);
        GarageWorkTime mockedGarageWorkTime = Mockito.mock(GarageWorkTime.class);
        when(garageRepository.findById(1L)).thenReturn(Optional.of(mockedGarage));
        when(garageRepository.save(mockedGarage)).thenReturn(mockedGarage);
        //When
        garageWorkTimeDbService.saveGarageWorkTime(mockedGarageWorkTime, 1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Garage", 1L));
        verify(garageRepository, times(1)).save(mockedGarage);
    }

    @Test
    void testDeleteGarageWorkTime() throws MyEntityNotFoundException {
        //Given
        GarageWorkTime mockedGarageWorkTime = Mockito.mock(GarageWorkTime.class);
        when(garageWorkTimeRepository.findById(1L)).thenReturn(Optional.of(mockedGarageWorkTime));
        doNothing().when(garageWorkTimeRepository).deleteById(1L);
        //When
        garageWorkTimeDbService.deleteGarageWorkTime(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("GarageWorkTime", 1L));
        verify(garageWorkTimeRepository, times(1)).deleteById(1L);
    }
}