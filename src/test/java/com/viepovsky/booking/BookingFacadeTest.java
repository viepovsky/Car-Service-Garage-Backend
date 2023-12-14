package com.viepovsky.booking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingFacadeTest {
    @InjectMocks
    private BookingFacade facade;

    @Mock
    private BookingService service;

    @Mock
    private BookingMapper mapper;

    @Test
    void shouldGetBookingsForGivenDateAndGarageId() {
        //Given
        var booking = new Booking();
        var bookingDto = new BookingDto();

        when(service.getBookingsByDateAndGarageId(any(LocalDate.class), anyLong())).thenReturn(List.of(booking));
        when(mapper.mapToBookingDtoList(anyList())).thenReturn(List.of(bookingDto));
        //When
        List<BookingDto> retrievedList = facade.getBookingsByDateAndGarageId(LocalDate.now(), 5L);
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldGetBookingsForGivenUsername() {
        //Given
        var booking = new Booking();
        var bookingDto = new BookingDto();

        when(service.getAllBookingsByUsername(anyString())).thenReturn(List.of(booking));
        when(mapper.mapToBookingDtoList(anyList())).thenReturn(List.of(bookingDto));
        //When
        List<BookingDto> retrievedList = facade.getBookingsByUsername("username");
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
























