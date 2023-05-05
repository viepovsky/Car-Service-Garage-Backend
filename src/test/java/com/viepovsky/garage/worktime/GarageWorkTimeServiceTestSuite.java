package com.viepovsky.garage.worktime;

import com.viepovsky.garage.Garage;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.garage.GarageRepository;
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
@DisplayName("Garage Work Time Db Service Test Suite")
class GarageWorkTimeServiceTestSuite {

    @InjectMocks
    private GarageWorkTimeService garageWorkTimeService;

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
        List<GarageWorkTime> retrievedGarageWorkTimeList = garageWorkTimeService.getAllGarageWorkTimes();
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
        garageWorkTimeService.saveGarageWorkTime(mockedGarageWorkTime, 1L);
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
        garageWorkTimeService.deleteGarageWorkTime(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("GarageWorkTime", 1L));
        verify(garageWorkTimeRepository, times(1)).deleteById(1L);
    }
}