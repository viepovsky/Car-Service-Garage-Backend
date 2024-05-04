package com.viepovsky.booking;

import com.viepovsky.vehicle.model.Vehicle;
import com.viepovsky.vehicle.VehicleService;
import com.viepovsky.car_repair.ServiceSelected;
import com.viepovsky.car_repair.CarRepairService;
import com.viepovsky.exceptions.WrongInputDataException;
import com.viepovsky.garage.model.Garage;
import com.viepovsky.garage.GarageService;
import com.viepovsky.vehicle_services.model.ServiceCatalog;
import com.viepovsky.vehicle_services.ServiceCatalogService;
import com.viepovsky.user.model.AppUser;
import com.viepovsky.user.UserService;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private GarageService garageService;

    @Mock
    private CarRepairService carRepairService;

    @Mock
    private VehicleService carService;

    @Mock
    private UserService userService;

    @Mock
    private ServiceCatalogService availableCarRepairService;

    @Test
    void testGetAllBookings() {
        //Given
        Visit mockedBooking = Mockito.mock(Visit.class);
        when(bookingRepository.findAll()).thenReturn(List.of(mockedBooking));
        //When
        List<Visit> retrievedList = bookingService.getAllBookings();
        //Then
        assertEquals(1, retrievedList.size());
    }

    @Test
    void testGetAllBookingsByUsername() {
        //Given
        Visit mockedBooking = Mockito.mock(Visit.class);
        AppUser mockedUser = Mockito.mock(AppUser.class);
        when(userService.getUser("username")).thenReturn(mockedUser);
        when(mockedUser.getId()).thenReturn(1L);
        when(bookingRepository.findBookingsByCarRepairList(1L)).thenReturn(List.of(mockedBooking));
        //When
        List<Visit> retrievedList = bookingService.getAllBookingsByUsername("username");
        //Then
        assertEquals(1, retrievedList.size());
    }

    @Test
    void testGetAvailableBookingTimesByDayAndRepairDuration() {
        //Given
        LocalDate localDate = LocalDate.now().plusDays(1);

        ServiceSelected carRepair = new ServiceSelected();
        carRepair.setRepairTimeInMinutes(50);

        List<ServiceSelected> carRepairList = List.of(carRepair);
        Garage garage = new Garage();
        garage.setId(5L);

        Visit bookedService = new Visit(BookingStatus.WAITING_FOR_CUSTOMER, localDate, LocalTime.of(10, 0), LocalTime.of(10, 50), null, carRepairList, garage);
        bookedService.setId(1L);
        carRepair.setBooking(bookedService);
        Visit booking = new Visit(BookingStatus.AVAILABLE, localDate, LocalTime.of(9, 50), LocalTime.of(11, 40), null, null, null);
        List<Visit> bookingList = new ArrayList<>();
        bookingList.add(booking);
        bookingList.add(bookedService);

        when(carRepairService.getCarRepair(anyLong())).thenReturn(carRepair);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(bookedService));
        when(bookingRepository.findBookingsByDateAndGarageId(localDate, 5L)).thenReturn(bookingList);
        //When
        List<LocalTime> retrievedAvailableTimeList = bookingService.getAvailableBookingTimesByDayAndRepairDuration(localDate, 20L);
        //Then
        List<LocalTime> expectedTimes = List.of(LocalTime.of(9, 50), LocalTime.of(10, 10), LocalTime.of(10, 20), LocalTime.of(10, 30), LocalTime.of(10, 40), LocalTime.of(10, 50));
        assertFalse(retrievedAvailableTimeList.isEmpty());
        assertEquals(6, retrievedAvailableTimeList.size());
        assertEquals(expectedTimes, retrievedAvailableTimeList);
    }

    @Test
    void testGetAvailableBookingTimesByDayAndRepairDurationThreeParameters() {
        //Given
        LocalDate localDate = LocalDate.now().plusDays(1);
        List<Visit> bookingList = List.of(new Visit(BookingStatus.AVAILABLE, localDate, LocalTime.of(9, 50), LocalTime.of(11, 40), null, null, null));
        when(bookingRepository.findBookingsByDateAndGarageId(localDate, 5L)).thenReturn(bookingList);
        //When
        List<LocalTime> retrievedAvailableTimeList = bookingService.getAvailableBookingTimesByDayAndRepairDuration(localDate, 50, 5L);
        //Then
        List<LocalTime> expectedTimes = List.of(LocalTime.of(9, 50), LocalTime.of(10, 0), LocalTime.of(10, 10), LocalTime.of(10, 20), LocalTime.of(10, 30), LocalTime.of(10, 40), LocalTime.of(10, 50));
        assertFalse(retrievedAvailableTimeList.isEmpty());
        assertEquals(7, retrievedAvailableTimeList.size());
        assertEquals(expectedTimes, retrievedAvailableTimeList);
    }

    @Test
    void shouldReturnEmptyArrayWhenGetAvailableBookingTimesParameterDayIsNotWorkingDay() {
        //Given
        LocalDate localDate = LocalDate.now().plusDays(1);
        List<Visit> bookingsOfGivenDate = List.of();
        when(bookingRepository.findBookingsByDateAndGarageId(localDate, 5L)).thenReturn(bookingsOfGivenDate);
        //When
        List<LocalTime> retrievedAvailableTimeList = bookingService.getAvailableBookingTimesByDayAndRepairDuration(localDate, 50, 5L);
        //Then
        assertTrue(retrievedAvailableTimeList.isEmpty());
    }

    @Test
    void testBookingSaveWhenNoOpenTimesSet() {
        //Given
        LocalDate localDate = LocalDate.now().plusDays(1);
        Garage mockedGarage = Mockito.mock(Garage.class);
        List<Visit> bookingList = new ArrayList<>();

        when(garageService.getGarage(anyLong())).thenReturn(mockedGarage);
        when(bookingRepository.findBookingsByDateAndStatusAndGarageId(localDate, BookingStatus.AVAILABLE, 50L)).thenReturn(bookingList);
        when(bookingRepository.save(any())).thenReturn(any());
        //When
        bookingService.createWorkingHoursBooking(localDate, LocalTime.of(8, 0), LocalTime.of(15, 0), 50L);
        //Then
        verify(bookingRepository, times(1)).save(any());
    }

    @Test
    void testBookingSaveWhenThereAreOpenTimes() {
        //Given
        LocalDate localDate = LocalDate.now().plusDays(1);
        Garage mockedGarage = Mockito.mock(Garage.class);
        Visit booking = new Visit(BookingStatus.AVAILABLE, localDate, LocalTime.of(8, 0), LocalTime.of(15, 0), null, null, null);
        booking.setId(1L);
        List<Visit> bookingList = new ArrayList<>();
        bookingList.add(booking);

        when(garageService.getGarage(anyLong())).thenReturn(mockedGarage);
        when(bookingRepository.findBookingsByDateAndStatusAndGarageId(localDate, BookingStatus.AVAILABLE, 50L)).thenReturn(bookingList);
        //When & then
        try {
            bookingService.createWorkingHoursBooking(localDate, LocalTime.of(8, 0), LocalTime.of(15, 0), 50L);
            fail("Expected an WrongInputDataException to be thrown");
        } catch (WrongInputDataException e) {
            assertThat(e.getMessage(), containsString("Work times of given date: " + localDate + ", are already declared"));
        }
        verify(bookingRepository, times(0)).save(any());
    }

    @Test
    void testUpdateBooking() {
        //Given
        LocalDate localDate = LocalDate.now().plusDays(1);
        LocalDate newLocalDate = LocalDate.now().plusDays(2);
        Visit booking = new Visit(BookingStatus.WAITING_FOR_CUSTOMER, localDate, LocalTime.of(11, 0), LocalTime.of(11, 30), null, null, null);

        when(bookingRepository.findById(50L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);
        //When
        bookingService.updateBooking(50L, newLocalDate, LocalTime.of(11, 20));
        //Then
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void testCreateBooking() {
        //Given
        LocalDate localDate = LocalDate.now().plusDays(1);
        int repairDuration = 70;
        Garage mockedGarage = Mockito.mock(Garage.class);
        AppUser user = new AppUser("Firstname", "Lastname", "email", "phonenumber", "username", "password");
        user.setId(1L);
        //todo fix it
        Vehicle car = Mockito.mock(Vehicle.class);
        user.setVehicles(List.of(car));
        List<LocalTime> localTimeList = List.of(LocalTime.of(10, 0), LocalTime.of(10, 10), LocalTime.of(10, 20), LocalTime.of(10, 30), LocalTime.of(10, 40), LocalTime.of(10, 50), LocalTime.of(11, 0));
        ServiceCatalog availableCarRepair = new ServiceCatalog(10L, "testname", "testdescription", BigDecimal.valueOf(50), 30, "BMW", BigDecimal.valueOf(1.2), mockedGarage);
        ServiceCatalog availableCarRepair2 = new ServiceCatalog(11L, "testname", "testdescription", BigDecimal.valueOf(70), 40, "AUDI", BigDecimal.valueOf(1.2), mockedGarage);

        when(garageService.getGarage(anyLong())).thenReturn(mockedGarage);
        when(carService.getCar(anyLong())).thenReturn(car);
        when(userService.getUser(anyLong())).thenReturn(user);
        BookingService bookingService = Mockito.spy(new BookingService(bookingRepository, garageService, carRepairService, carService, userService, availableCarRepairService));
        Mockito.doReturn(localTimeList).when(bookingService).getAvailableBookingTimesByDayAndRepairDuration(localDate, repairDuration, 5L);
        when(bookingRepository.save(any())).thenReturn(any());
        when(availableCarRepairService.getAvailableCarRepair(10L)).thenReturn(availableCarRepair);
        when(availableCarRepairService.getAvailableCarRepair(11L)).thenReturn(availableCarRepair2);
        when(userService.saveUser(any(AppUser.class))).thenReturn(Mockito.mock(AppUser.class));
        //When
        bookingService.createBooking(List.of(10L, 11L), localDate, LocalTime.of(10, 0), 5L, 2L, repairDuration);
        //Then
        verify(userService, times(1)).saveUser(any(AppUser.class));
        verify(bookingRepository, times(2)).save(any());
    }
}