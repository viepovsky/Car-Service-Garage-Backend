package com.viepovsky.booking;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.exceptions.WrongInputDataException;
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
class BookingFacadeTestSuite {
    @InjectMocks
    private BookingFacade facade;

    @Mock
    private BookingService service;

    @Mock
    private BookingMapper mapper;

    @Test
    void shouldGetBookingsForGivenDateAndGarageId() throws MyEntityNotFoundException {
        //Given
        var booking = new Booking();
        var bookingDto = new BookingDto();

        when(service.getBookingsForGivenDateAndGarageId(any(LocalDate.class), anyLong())).thenReturn(List.of(booking));
        when(mapper.mapToBookingDtoList(anyList())).thenReturn(List.of(bookingDto));
        //When
        List<BookingDto> retrievedList = facade.getBookingsForGivenDateAndGarageId(LocalDate.now(), 5L);
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldGetBookingsForGivenUsername() throws MyEntityNotFoundException {
        //Given
        var booking = new Booking();
        var bookingDto = new BookingDto();

        when(service.getAllBookingsForGivenUser(anyString())).thenReturn(List.of(booking));
        when(mapper.mapToBookingDtoList(anyList())).thenReturn(List.of(bookingDto));
        //When
        List<BookingDto> retrievedList = facade.getBookingsForGivenUsername("username");
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldGetAvailableBookingTimesTwoParams() throws MyEntityNotFoundException {
        //Given
        LocalDate date = LocalDate.now();
        when(service.getAvailableBookingTimesForSelectedDayAndRepairDuration(any(LocalDate.class), anyLong())).thenReturn(List.of(LocalTime.now()));
        //When
        List<LocalTime> retrievedList = facade.getAvailableBookingTimes(date, 50, 2L, 1L);
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldGetAvailableBookingTimesThreeParams() throws MyEntityNotFoundException {
        //Given
        LocalDate date = LocalDate.now();
        when(service.getAvailableBookingTimesForSelectedDayAndRepairDuration(any(LocalDate.class), anyInt(), anyLong())).thenReturn(List.of(LocalTime.now()));
        //When
        List<LocalTime> retrievedList = facade.getAvailableBookingTimes(date, 50, 2L, 0L);
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldCreateBooking() throws MyEntityNotFoundException, WrongInputDataException {
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
    void shouldCreateAvailableBookingDays() throws MyEntityNotFoundException, WrongInputDataException {
        //Given
        LocalDate date = LocalDate.now();
        LocalTime start = LocalTime.now();
        LocalTime end = start.plusMinutes(50);
        doNothing().when(service).saveBooking(date, start, end, 2L);
        //When
        facade.createWorkingHoursBooking(date, start, end, 2L);
        //Then
        verify(service, times(1)).saveBooking(date, start, end, 2L);
    }

    @Test
    void shouldUpdateBooking() throws MyEntityNotFoundException {
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
























