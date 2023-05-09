package com.viepovsky.garage.worktime;

import com.viepovsky.garage.Garage;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.garage.GarageService;
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
    private GarageWorkTimeService workTimeService;

    @Mock
    private GarageWorkTimeRepository workTimeRepository;

    @Mock
    private GarageService garageService;

    @Test
    void testGetAllGarageWorkTimes() {
        //Given
        List<GarageWorkTime> garageWorkTimeList = new ArrayList<>();
        GarageWorkTime garageWorkTime = Mockito.mock(GarageWorkTime.class);
        garageWorkTimeList.add(garageWorkTime);
        when(workTimeRepository.findAllByGarageId(anyLong())).thenReturn(garageWorkTimeList);
        //When
        List<GarageWorkTime> retrievedGarageWorkTimeList = workTimeService.getAllGarageWorkTimes(5L);
        //Then
        assertEquals(1, retrievedGarageWorkTimeList.size());
    }

    @Test
    void testSaveGarageWorkTime() throws MyEntityNotFoundException {
        //Given
        Garage mockedGarage = Mockito.mock(Garage.class);
        GarageWorkTime mockedGarageWorkTime = Mockito.mock(GarageWorkTime.class);
        when(garageService.getGarage(anyLong())).thenReturn(mockedGarage);
        when(garageService.saveGarage(any(Garage.class))).thenReturn(mockedGarage);
        //When
        workTimeService.saveGarageWorkTime(mockedGarageWorkTime, 1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Garage", 1L));
        verify(garageService, times(1)).saveGarage(any(Garage.class));
    }

    @Test
    void testDeleteGarageWorkTime() throws MyEntityNotFoundException {
        //Given
        GarageWorkTime mockedGarageWorkTime = Mockito.mock(GarageWorkTime.class);
        when(workTimeRepository.findById(1L)).thenReturn(Optional.of(mockedGarageWorkTime));
        doNothing().when(workTimeRepository).deleteById(1L);
        //When
        workTimeService.deleteGarageWorkTime(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("GarageWorkTime", 1L));
        verify(workTimeRepository, times(1)).deleteById(1L);
    }
}