package com.viepovsky.visit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.viepovsky.utility.mapper.VisitMapper;
import com.viepovsky.visit.dto.VisitDto;
import com.viepovsky.visit.model.Visit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class BookingFacadeTest {
    @InjectMocks
    private VisitFacade facade;

    @Mock
    private VisitService service;

    @Mock
    private VisitMapper mapper;

    @Test
    void shouldGetBookingsForGivenDateAndGarageId() {
        //Given
        var booking = new Visit();
        var bookingDto = new VisitDto();

        when(service.getVisitsForGarageAndDate(anyLong(), any(LocalDate.class))).thenReturn(List.of(booking));
        when(mapper.mapToBookingDtoList(anyList())).thenReturn(List.of(bookingDto));
        //When
        List<VisitDto> retrievedList = facade.getVisitsForGarageAndDate(5L, LocalDate.now());
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldGetBookingsForGivenUsername() {
        //Given
        var booking = new Visit();
        var bookingDto = new VisitDto();

        when(service.getAllBookingsByUsername(anyString())).thenReturn(List.of(booking));
        when(mapper.mapToBookingDtoList(anyList())).thenReturn(List.of(bookingDto));
        //When
        List<VisitDto> retrievedList = facade.getBookingsByUsername("username");
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldGetAvailableBookingTimesTwoParams() {
        //Given
        LocalDate date = LocalDate.now();
        when(service.getAvailableBookingTimesByDayAndRepairDuration(any(LocalDate.class), anyLong())).thenReturn(List.of(LocalTime.now()));
        //When
        List<LocalTime> retrievedList = facade.getAvailableBookingTimes(date, 50, 2L, 1L);
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldGetAvailableBookingTimesThreeParams() {
        //Given
        LocalDate date = LocalDate.now();
        when(service.getAvailableBookingTimesByDayAndRepairDuration(any(LocalDate.class), anyInt(), anyLong())).thenReturn(List.of(LocalTime.now()));
        //When
        List<LocalTime> retrievedList = facade.getAvailableBookingTimes(date, 50, 2L, 0L);
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldCreateBooking() {
        //Given
        LocalDate date = LocalDate.now();
        LocalTime start = LocalTime.now();
        doNothing().when(service).createBooking(List.of(1L, 2L), date, start, 1L, 2L, 50);
        //When
        facade.createBooking(List.of(1L, 2L), date, start, 1L, 2L, 50);
        //Then
        verify(service, times(1)).createBooking(List.of(1L, 2L), date, start, 1L, 2L, 50);
    }

    @Test
    void shouldCreateAvailableBookingDays() {
        //Given
        LocalDate date = LocalDate.now();
        LocalTime start = LocalTime.now();
        LocalTime end = start.plusMinutes(50);
        doNothing().when(service).createWorkingHoursBooking(date, start, end, 2L);
        //When
        facade.createWorkingHoursBooking(date, start, end, 2L);
        //Then
        verify(service, times(1)).createWorkingHoursBooking(date, start, end, 2L);
    }

    @Test
    void shouldUpdateBooking() {
        //Given
        LocalDate date = LocalDate.now();
        LocalTime start = LocalTime.now();
        doNothing().when(service).updateBooking(2L, date, start);
        //When
        facade.updateBooking(2L, date, start);
        //Then
        verify(service, times(1)).updateBooking(2L, date, start);
    }

}
























