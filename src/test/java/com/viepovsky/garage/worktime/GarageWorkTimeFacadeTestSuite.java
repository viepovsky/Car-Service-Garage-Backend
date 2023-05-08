package com.viepovsky.garage.worktime;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.scheduler.ApplicationScheduler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
@MockBean(ApplicationScheduler.class)
class GarageWorkTimeFacadeTestSuite {
    @InjectMocks
    private GarageWorkTimeFacade garageWorkTimeFacade;

    @Mock
    private GarageWorkTimeService garageWorkTimeService;

    @Mock
    private GarageWorkTimeMapper garageWorkTimeMapper;

    @Test
    void shouldCreateGarageWorkTime() throws MyEntityNotFoundException {
        //Given
        GarageWorkTimeDto mockedDto = Mockito.mock(GarageWorkTimeDto.class);
        GarageWorkTime mocked = Mockito.mock(GarageWorkTime.class);
        when(garageWorkTimeMapper.mapToGarageWorkTime(mockedDto)).thenReturn(mocked);
        doNothing().when(garageWorkTimeService).saveGarageWorkTime(mocked, 1L);
        //When
        garageWorkTimeFacade.createGarageWorkTime(mockedDto, 1L);
        //Then
        verify(garageWorkTimeService, times(1)).saveGarageWorkTime(mocked, 1L);
    }

    @Test
    void shouldDeleteGarageWorkTime() throws MyEntityNotFoundException {
        //Given
        doNothing().when(garageWorkTimeService).deleteGarageWorkTime(1L);
        //When
        garageWorkTimeFacade.deleteGarageWorkTime(1L);
        //Then
        verify(garageWorkTimeService, times(1)).deleteGarageWorkTime(1L);
    }
}