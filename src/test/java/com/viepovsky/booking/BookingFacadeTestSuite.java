package com.viepovsky.booking;

import com.viepovsky.config.AdminConfig;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.exceptions.WrongInputDataException;
import com.viepovsky.scheduler.ApplicationScheduler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@MockBean(ApplicationScheduler.class)
class BookingFacadeTestSuite {
    @InjectMocks
    private BookingFacade bookingFacade;

    @Mock
    private BookingService bookingService;

    @Mock
    private BookingMapper bookingMapper;

    @Mock
    private AdminConfig adminConfig;

    @Value("${admin.api.key}")
    private String adminApiKey;

    @Test
    void shouldGetBookingsForGivenUsername() throws MyEntityNotFoundException {
        //Given
        Booking mockedBooking = Mockito.mock(Booking.class);
        BookingDto mockedBookingDto = Mockito.mock(BookingDto.class);
        when(bookingService.getAllBookingsForGivenUser("username")).thenReturn(List.of(mockedBooking));
        when(bookingMapper.mapToBookingDtoList(List.of(mockedBooking))).thenReturn(List.of(mockedBookingDto));
        //When
        List<BookingDto> retrievedList = bookingFacade.getBookingsForGivenUsername("username");
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldGetAvailableBookingTimesTwoParams() throws MyEntityNotFoundException {
        //Given
        LocalDate date = LocalDate.now();
        when(bookingService.getAvailableBookingTimesForSelectedDayAndRepairDuration(date, 1L)).thenReturn(List.of(LocalTime.now()));
        //When
        List<LocalTime> retrievedList = bookingFacade.getAvailableBookingTimes(date, 50, 2L, 1L);
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldGetAvailableBookingTimesThreeParams() throws MyEntityNotFoundException {
        //Given
        LocalDate date = LocalDate.now();
        when(bookingService.getAvailableBookingTimesForSelectedDayAndRepairDuration(date, 50, 2L)).thenReturn(List.of(LocalTime.now()));
        //When
        List<LocalTime> retrievedList = bookingFacade.getAvailableBookingTimes(date, 50, 2L, 0L);
        //Then
        assertNotNull(retrievedList);
        assertEquals(1, retrievedList.size());
    }

    @Test
    void shouldCreateBooking() throws MyEntityNotFoundException, WrongInputDataException {
        //Given
        LocalDate date = LocalDate.now();
        LocalTime start = LocalTime.now();
        doNothing().when(bookingService).createBooking(List.of(1L, 2L), date, start, 1L, 2L, 50);
        //When
        bookingFacade.createBooking(List.of(1L, 2L), date, start, 1L, 2L, 50);
        //Then
        verify(bookingService, times(1)).createBooking(List.of(1L, 2L), date, start, 1L, 2L, 50);
    }

    @Test
    void shouldCreateAvailableBookingDays() throws MyEntityNotFoundException, WrongInputDataException {
        //Given
        LocalDate date = LocalDate.now();
        LocalTime start = LocalTime.now();
        LocalTime end = start.plusMinutes(50);
        doNothing().when(bookingService).saveBooking(date, start, end, 2L);
        //When
        bookingFacade.createWorkingHoursBooking(date, start, end, 2L);
        //Then
        verify(bookingService, times(1)).saveBooking(date, start, end, 2L);
    }

    @Test
    void shouldUpdateBooking() throws MyEntityNotFoundException {
        //Given
        LocalDate date = LocalDate.now();
        LocalTime start = LocalTime.now();
        doNothing().when(bookingService).updateBooking(2L, date, start);
        //When
        bookingFacade.updateBooking(2L, date, start);
        //Then
        verify(bookingService, times(1)).updateBooking(2L, date, start);
    }

}
























