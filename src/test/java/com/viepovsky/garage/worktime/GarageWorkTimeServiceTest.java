package com.viepovsky.garage.worktime;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.garage.Garage;
import com.viepovsky.garage.GarageService;
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
@DisplayName("Garage Work Time Db Service Tests")
class GarageWorkTimeServiceTest {

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
        var garageWorkTime = new GarageWorkTime();
        garageWorkTimeList.add(garageWorkTime);

        when(workTimeRepository.findAllByGarageId(anyLong())).thenReturn(garageWorkTimeList);
        //When
        List<GarageWorkTime> retrievedGarageWorkTimeList = workTimeService.getAllGarageWorkTimes(5L);
        //Then
        assertEquals(1, retrievedGarageWorkTimeList.size());
    }

    @Test
    void testSaveGarageWorkTime() {
        //Given
        var garage = new Garage();
        var garageWorkTime = new GarageWorkTime();

        when(garageService.getGarage(anyLong())).thenReturn(garage);
        when(garageService.saveGarage(any(Garage.class))).thenReturn(garage);
        //When
        workTimeService.saveGarageWorkTime(garageWorkTime, 1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("Garage", 1L));
        verify(garageService, times(1)).saveGarage(any(Garage.class));
    }

    @Test
    void testDeleteGarageWorkTime() {
        //Given
        var workTime = new GarageWorkTime();
        when(workTimeRepository.findById(anyLong())).thenReturn(Optional.of(workTime));
        doNothing().when(workTimeRepository).deleteById(anyLong());
        //When
        workTimeService.deleteGarageWorkTime(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("GarageWorkTime", 1L));
        verify(workTimeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteWorkTimeShouldThrowExceptionIfWorkTimeDoesNotExist() {
        //Given
        when(workTimeRepository.findById(anyLong())).thenReturn(Optional.empty());
        //When & then
        assertThrows(MyEntityNotFoundException.class, () -> workTimeService.deleteGarageWorkTime(1L));
    }
}