package com.viepovsky.garage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.viepovsky.garage.model.Garage;
import com.viepovsky.garage.model.Schedule;
import com.viepovsky.utility.exceptions.MyEntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@DisplayName("Garage Work Time Db Service Tests")
class GarageWorkTimeServiceTest {

    @InjectMocks
    private ScheduleService workTimeService;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private GarageService garageService;

    @Test
    void testGetAllGarageWorkTimes() {
        //Given
        List<Schedule> garageWorkTimeList = new ArrayList<>();
        var garageWorkTime = new Schedule();
        garageWorkTimeList.add(garageWorkTime);

        when(scheduleRepository.findAllByGarageId(anyLong())).thenReturn(garageWorkTimeList);
        //When
        List<Schedule> retrievedGarageWorkTimeList = workTimeService.getAllGarageWorkTimes(5L);
        //Then
        assertEquals(1, retrievedGarageWorkTimeList.size());
    }

    @Test
    void testSaveGarageWorkTime() {
        //Given
        var garage = new Garage();
        var garageWorkTime = new Schedule();

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
        when(scheduleRepository.existsById(1L)).thenReturn(true);
        doNothing().when(scheduleRepository).deleteById(anyLong());
        //When
        workTimeService.deleteGarageWorkTime(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("GarageWorkTime", 1L));
        verify(scheduleRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteWorkTimeShouldThrowExceptionIfWorkTimeDoesNotExist() {
        //Given
        when(scheduleRepository.existsById(1L)).thenReturn(false);
        //When & then
        assertThrows(MyEntityNotFoundException.class, () -> workTimeService.deleteGarageWorkTime(1L));
    }
}