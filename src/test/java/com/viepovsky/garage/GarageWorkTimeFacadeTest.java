package com.viepovsky.garage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.viepovsky.garage.dto.ScheduleDto;
import com.viepovsky.garage.model.Schedule;
import com.viepovsky.utility.mapper.ScheduleMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class GarageWorkTimeFacadeTest {
    @InjectMocks
    private ScheduleFacade facade;

    @Mock
    private ScheduleService service;

    @Mock
    private ScheduleMapper mapper;

    @Test
    void shouldGetGarageWorkTImes() {
        //Given
        List<Schedule> workTimes = List.of(new Schedule());
        List<ScheduleDto> workTimesResponse = List.of(ScheduleDto.builder().build());

        when(service.getAllGarageWorkTimes(anyLong())).thenReturn(workTimes);
        when(mapper.mapToGarageWorkTimeDtoList(anyList())).thenReturn(workTimesResponse);
        //When
        List<ScheduleDto> retrievedWorkTimes = facade.getGarageWorkTimes(5L);
        //Then
        assertEquals(1, retrievedWorkTimes.size());
    }

    @Test
    void shouldCreateGarageWorkTime() {
        //Given
        var workTimeDto = ScheduleDto.builder().build();
        var workTime = new Schedule();

        when(mapper.mapToGarageWorkTime(any(ScheduleDto.class))).thenReturn(workTime);
        doNothing().when(service).saveGarageWorkTime(any(Schedule.class), anyLong());
        //When
        facade.createGarageWorkTime(workTimeDto, 1L);
        //Then
        verify(service, times(1)).saveGarageWorkTime(workTime, 1L);
    }

    @Test
    void shouldDeleteGarageWorkTime() {
        //Given
        doNothing().when(service).deleteGarageWorkTime(anyLong());
        //When
        facade.deleteGarageWorkTime(1L);
        //Then
        verify(service, times(1)).deleteGarageWorkTime(1L);
    }
}