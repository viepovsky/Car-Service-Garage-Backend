package com.viepovsky.garage.worktime;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GarageWorkTimeFacadeTestSuite {
    @InjectMocks
    private GarageWorkTimeFacade facade;

    @Mock
    private GarageWorkTimeService service;

    @Mock
    private GarageWorkTimeMapper mapper;

    @Test
    void shouldGetGarageWorkTImes() {
        //Given
        List<GarageWorkTime> workTimes = List.of(new GarageWorkTime());
        List<GarageWorkTimeDto> workTimesResponse = List.of(GarageWorkTimeDto.builder().build());

        when(service.getAllGarageWorkTimes(anyLong())).thenReturn(workTimes);
        when(mapper.mapToGarageWorkTimeDtoList(anyList())).thenReturn(workTimesResponse);
        //When
        List<GarageWorkTimeDto> retrievedWorkTimes = facade.getGarageWorkTimes(5L);
        //Then
        assertEquals(1, retrievedWorkTimes.size());
    }

    @Test
    void shouldCreateGarageWorkTime() throws MyEntityNotFoundException {
        //Given
        var workTimeDto = GarageWorkTimeDto.builder().build();
        var workTime = new GarageWorkTime();

        when(mapper.mapToGarageWorkTime(any(GarageWorkTimeDto.class))).thenReturn(workTime);
        doNothing().when(service).saveGarageWorkTime(any(GarageWorkTime.class), anyLong());
        //When
        facade.createGarageWorkTime(workTimeDto, 1L);
        //Then
        verify(service, times(1)).saveGarageWorkTime(workTime, 1L);
    }

    @Test
    void shouldDeleteGarageWorkTime() throws MyEntityNotFoundException {
        //Given
        doNothing().when(service).deleteGarageWorkTime(anyLong());
        //When
        facade.deleteGarageWorkTime(1L);
        //Then
        verify(service, times(1)).deleteGarageWorkTime(1L);
    }
}