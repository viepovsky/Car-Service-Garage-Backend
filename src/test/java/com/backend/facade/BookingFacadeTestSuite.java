package com.backend.facade;

import com.backend.config.AdminConfig;
import com.backend.domain.Booking;
import com.backend.domain.dto.BookingDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.exceptions.WrongInputDataException;
import com.backend.mapper.BookingMapper;
import com.backend.scheduler.ApplicationScheduler;
import com.backend.service.BookingDbService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@MockBean(ApplicationScheduler.class)
class BookingFacadeTestSuite {
    @InjectMocks
    private BookingFacade bookingFacade;

    @Mock
    private BookingDbService bookingDbService;

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
        when(bookingDbService.getAllBookingsForGivenUser("username")).thenReturn(List.of(mockedBooking));
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
        when(bookingDbService.getAvailableBookingTimesForSelectedDayAndRepairDuration(date, 1L)).thenReturn(List.of(LocalTime.now()));
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
        when(bookingDbService.getAvailableBookingTimesForSelectedDayAndRepairDuration(date, 50, 2L)).thenReturn(List.of(LocalTime.now()));
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
        doNothing().when(bookingDbService).createBooking(List.of(1L, 2L), date, start, 1L, 2L, 50);
        //When
        bookingFacade.createBooking(List.of(1L, 2L), date, start, 1L, 2L, 50);
        //Then
        verify(bookingDbService, times(1)).createBooking(List.of(1L, 2L), date, start, 1L, 2L, 50);
    }

    @Test
    void shouldCreateAvailableBookingDays() throws MyEntityNotFoundException, WrongInputDataException {
        //Given
        LocalDate date = LocalDate.now();
        LocalTime start = LocalTime.now();
        LocalTime end = start.plusMinutes(50);
        when(adminConfig.getAdminApiKey()).thenReturn(adminApiKey);
        doNothing().when(bookingDbService).saveBooking(date, start, end, 2L);
        //When
        ResponseEntity<String> response = bookingFacade.createAvailableBookingDays(date, start, end, 2L, adminApiKey);
        //Then
        verify(bookingDbService, times(1)).saveBooking(date, start, end, 2L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldNotCreateAvailableBookingDays() throws MyEntityNotFoundException, WrongInputDataException {
        //Given
        LocalDate date = LocalDate.now();
        LocalTime start = LocalTime.now();
        LocalTime end = start.plusMinutes(50);
        when(adminConfig.getAdminApiKey()).thenReturn(adminApiKey);
        doNothing().when(bookingDbService).saveBooking(date, start, end, 2L);
        //When
        ResponseEntity<String> response = bookingFacade.createAvailableBookingDays(date, start, end, 2L, "55d");
        //Then
        verify(bookingDbService, times(0)).saveBooking(date, start, end, 2L);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void shouldUpdateBooking() throws MyEntityNotFoundException {
        //Given
        LocalDate date = LocalDate.now();
        LocalTime start = LocalTime.now();
        doNothing().when(bookingDbService).updateBooking(2L, date, start);
        //When
        bookingFacade.updateBooking(2L, date, start);
        //Then
        verify(bookingDbService, times(1)).updateBooking(2L, date, start);
    }

}
























