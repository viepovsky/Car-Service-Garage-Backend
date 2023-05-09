package com.viepovsky.garage.worktime;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void shouldCreateGarageWorkTime() throws MyEntityNotFoundException {
        //Given
        GarageWorkTimeDto mockedDto = Mockito.mock(GarageWorkTimeDto.class);
        GarageWorkTime mocked = Mockito.mock(GarageWorkTime.class);
        when(mapper.mapToGarageWorkTime(mockedDto)).thenReturn(mocked);
        doNothing().when(service).saveGarageWorkTime(mocked, 1L);
        //When
        facade.createGarageWorkTime(mockedDto, 1L);
        //Then
        verify(service, times(1)).saveGarageWorkTime(mocked, 1L);
    }

    @Test
    void shouldDeleteGarageWorkTime() throws MyEntityNotFoundException {
        //Given
        doNothing().when(service).deleteGarageWorkTime(1L);
        //When
        facade.deleteGarageWorkTime(1L);
        //Then
        verify(service, times(1)).deleteGarageWorkTime(1L);
    }
}