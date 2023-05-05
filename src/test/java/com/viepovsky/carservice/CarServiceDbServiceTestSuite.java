package com.viepovsky.carservice;

import com.viepovsky.booking.Booking;
import com.viepovsky.booking.BookingRepository;
import com.viepovsky.booking.BookingStatus;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.user.User;
import com.viepovsky.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Car Service Db Service Test Suite")
class CarServiceDbServiceTestSuite {
    @InjectMocks
    private CarServiceDbService carServiceDbService;

    @Mock
    private CarServiceRepository carServiceRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Test
    void testGetCarServices() throws MyEntityNotFoundException {
        //Given
        List<CarService> carServiceList = new ArrayList<>();
        CarService mockedCarService = Mockito.mock(CarService.class);
        carServiceList.add(mockedCarService);
        User mockedUser = Mockito.mock(User.class);
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(mockedUser));
        when(mockedUser.getId()).thenReturn(1L);
        when(carServiceRepository.findCarServicesByUserId(1L)).thenReturn(carServiceList);
        //When
        List<CarService> retrievedCarServiceList = carServiceDbService.getCarServices("username");
        //Then
        assertEquals(1, retrievedCarServiceList.size());
    }

    @Test
    void testDeleteCarServiceMoreThanOneService() throws MyEntityNotFoundException {
        //Given
        Booking booking = new Booking(BookingStatus.WAITING_FOR_CUSTOMER, LocalDate.now().plusDays(2), LocalTime.of(10,0), LocalTime.of(12,0), LocalDateTime.now(), BigDecimal.valueOf(300), new ArrayList<>(), null);
        CarService carService = new CarService("Testname","Testdescription", BigDecimal.valueOf(50), 30, null, null, booking, ServiceStatus.AWAITING);
        CarService carService2 = new CarService("Testname2","Testdescription2", BigDecimal.valueOf(250), 90, null, null, booking, ServiceStatus.AWAITING);
        booking.getCarServiceList().add(carService);
        booking.getCarServiceList().add(carService2);

        when(carServiceRepository.findById(1L)).thenReturn(Optional.of(carService));
        doNothing().when(carServiceRepository).delete(carService);
        when(bookingRepository.save(booking)).thenReturn(booking);
        //When
        carServiceDbService.deleteCarService(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("CarService", 1L));
        assertEquals(1, booking.getCarServiceList().size());
        assertEquals(BigDecimal.valueOf(250), booking.getTotalCost());
        assertEquals(LocalTime.of(10, 0), booking.getStartHour());
        assertEquals(LocalTime.of(11, 30), booking.getEndHour());
        verify(carServiceRepository, times(1)).delete(carService);
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void testDeleteCarServiceOnlyOneService() throws MyEntityNotFoundException {
        //Given
        Booking booking = new Booking(BookingStatus.WAITING_FOR_CUSTOMER, LocalDate.now().plusDays(2), LocalTime.of(10,0), LocalTime.of(10,30), LocalDateTime.now(), BigDecimal.valueOf(50), new ArrayList<>(), null);
        CarService carService = new CarService("Testname","Testdescription", BigDecimal.valueOf(50), 30, null, null, booking, ServiceStatus.AWAITING);
        booking.getCarServiceList().add(carService);

        when(carServiceRepository.findById(1L)).thenReturn(Optional.of(carService));
        doNothing().when(carServiceRepository).delete(carService);
        doNothing().when(bookingRepository).delete(booking);
        //When
        carServiceDbService.deleteCarService(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("CarService", 1L));
        verify(carServiceRepository, times(1)).delete(carService);
        verify(bookingRepository, times(1)).delete(booking);
    }
}