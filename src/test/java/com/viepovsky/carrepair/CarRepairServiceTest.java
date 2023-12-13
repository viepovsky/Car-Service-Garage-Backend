package com.viepovsky.carrepair;

import com.viepovsky.booking.Booking;
import com.viepovsky.booking.BookingService;
import com.viepovsky.booking.BookingStatus;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.user.User;
import com.viepovsky.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Car Service Db Service Tests")
class CarRepairServiceTest {
    @InjectMocks
    private CarRepairService carRepairService;

    @Mock
    private CarRepairRepository carRepairRepository;

    @Mock
    private UserService userService;

    @Mock
    private BookingService bookingService;

    @Test
    void testGetCarRepairs() throws MyEntityNotFoundException {
        //Given
        List<CarRepair> carRepairList = new ArrayList<>();
        CarRepair mockedCarRepair = Mockito.mock(CarRepair.class);
        carRepairList.add(mockedCarRepair);
        User mockedUser = Mockito.mock(User.class);
        when(userService.getUser(anyString())).thenReturn(mockedUser);
        when(mockedUser.getId()).thenReturn(1L);
        when(carRepairRepository.findCarServicesByUserId(1L)).thenReturn(carRepairList);
        //When
        List<CarRepair> retrievedCarRepairList = carRepairService.getCarRepairs("username");
        //Then
        assertEquals(1, retrievedCarRepairList.size());
    }

    @Test
    void shouldGetCarRepair() {
        //Given
        var carRepair = new CarRepair();
        when(carRepairRepository.findById(anyLong())).thenReturn(Optional.of(carRepair));
        //When
        var retrievedCarRepair = carRepairService.getCarRepair(5L);
        //Then
        assertNotNull(retrievedCarRepair);
    }

    @Test
    void testDeleteCarServiceMoreThanOneService() throws MyEntityNotFoundException {
        //Given
        Booking booking = new Booking(BookingStatus.WAITING_FOR_CUSTOMER, LocalDate.now().plusDays(2), LocalTime.of(10, 0), LocalTime.of(12, 0), BigDecimal.valueOf(300), new ArrayList<>(), null);
        CarRepair carRepair = new CarRepair("Testname", "Testdescription", BigDecimal.valueOf(50), 30, null, null, booking, RepairStatus.AWAITING);
        CarRepair carRepair2 = new CarRepair("Testname2", "Testdescription2", BigDecimal.valueOf(250), 90, null, null, booking, RepairStatus.AWAITING);
        booking.getCarRepairList().add(carRepair);
        booking.getCarRepairList().add(carRepair2);

        when(carRepairRepository.findById(1L)).thenReturn(Optional.of(carRepair));
        doNothing().when(carRepairRepository).delete(carRepair);
        doNothing().when(bookingService).save(any(Booking.class));
        //When
        carRepairService.deleteCarRepair(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("CarService", 1L));
        assertEquals(1, booking.getCarRepairList().size());
        assertEquals(BigDecimal.valueOf(250), booking.getTotalCost());
        assertEquals(LocalTime.of(10, 0), booking.getStartHour());
        assertEquals(LocalTime.of(11, 30), booking.getEndHour());
        verify(carRepairRepository, times(1)).delete(carRepair);
        verify(bookingService, times(1)).save(any(Booking.class));
    }

    @Test
    void testDeleteCarServiceOnlyOneService() throws MyEntityNotFoundException {
        //Given
        Booking booking = new Booking(BookingStatus.WAITING_FOR_CUSTOMER, LocalDate.now().plusDays(2), LocalTime.of(10, 0), LocalTime.of(10, 30), BigDecimal.valueOf(50), new ArrayList<>(), null);
        CarRepair carRepair = new CarRepair("Testname", "Testdescription", BigDecimal.valueOf(50), 30, null, null, booking, RepairStatus.AWAITING);
        booking.getCarRepairList().add(carRepair);

        when(carRepairRepository.findById(1L)).thenReturn(Optional.of(carRepair));
        doNothing().when(carRepairRepository).delete(carRepair);
        doNothing().when(bookingService).delete(booking);
        //When
        carRepairService.deleteCarRepair(1L);
        //Then
        assertDoesNotThrow(() -> new MyEntityNotFoundException("CarService", 1L));
        verify(carRepairRepository, times(1)).delete(carRepair);
        verify(bookingService, times(1)).delete(booking);
    }
}