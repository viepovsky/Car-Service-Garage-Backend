package com.viepovsky.booking;

import com.viepovsky.car.Car;
import com.viepovsky.car.CarService;
import com.viepovsky.carservice.CarRepair;
import com.viepovsky.carservice.CarRepairService;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.exceptions.WrongInputDataException;
import com.viepovsky.garage.Garage;
import com.viepovsky.garage.GarageService;
import com.viepovsky.garage.availablerepair.AvailableCarRepair;
import com.viepovsky.garage.availablerepair.AvailableCarRepairService;
import com.viepovsky.user.User;
import com.viepovsky.user.UserService;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTestSuite {
    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private GarageService garageService;

    @Mock
    private CarRepairService carRepairService;

    @Mock
    private CarService carService;

    @Mock
    private UserService userService;

    @Mock
    private AvailableCarRepairService availableCarRepairService;

    @Test
    void testGetAllBookings() {
        //Given
        Booking mockedBooking = Mockito.mock(Booking.class);
        when(bookingRepository.findAll()).thenReturn(List.of(mockedBooking));
        //When
        List<Booking> retrievedList = bookingService.getAllBookings();
        //Then
        assertEquals(1, retrievedList.size());
    }

    @Test
    void testGetAllBookingsForGivenUsername() throws MyEntityNotFoundException {
        //Given
        Booking mockedBooking = Mockito.mock(Booking.class);
        User mockedUser = Mockito.mock(User.class);
        when(userService.getUser("username")).thenReturn(mockedUser);
        when(mockedUser.getId()).thenReturn(1L);
        when(bookingRepository.findBookingsByCarRepairListUserId(1L)).thenReturn(List.of(mockedBooking));
        //When
        List<Booking> retrievedList = bookingService.getAllBookingsForGivenUser("username");
        //Then
        assertEquals(1, retrievedList.size());
    }

    @Test
    void testGetAvailableBookingTimesForDayAndRepairDuration() throws MyEntityNotFoundException {
        //Given
        LocalDate localDate = LocalDate.now().plusDays(1);

        CarRepair carRepair = new CarRepair();
        carRepair.setRepairTimeInMinutes(50);

        List<CarRepair> carRepairList = List.of(carRepair);
        Garage garage = new Garage();
        garage.setId(5L);

        Booking bookedService = new Booking(BookingStatus.WAITING_FOR_CUSTOMER, localDate, LocalTime.of(10,0), LocalTime.of(10,50), null, carRepairList, garage);
        bookedService.setId(1L);
        carRepair.setBooking(bookedService);
        Booking booking = new Booking(BookingStatus.AVAILABLE, localDate, LocalTime.of(9,50), LocalTime.of(11,40), null, null, null);
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        bookingList.add(bookedService);

        when(carRepairService.getCarService(anyLong())).thenReturn(carRepair);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(bookedService));
        when(bookingRepository.findBookingsByDateAndGarageId(localDate, 5L)).thenReturn(bookingList);
        //When
        List<LocalTime> retrievedAvailableTimeList = bookingService.getAvailableBookingTimesForSelectedDayAndRepairDuration(localDate, 20L);
        //Then
        List<LocalTime> expectedTimes = List.of(LocalTime.of(9, 50), LocalTime.of(10,10), LocalTime.of(10, 20),LocalTime.of(10, 30),LocalTime.of(10, 40), LocalTime.of(10, 50));
        assertFalse(retrievedAvailableTimeList.isEmpty());
        assertEquals(6, retrievedAvailableTimeList.size());
        assertEquals(expectedTimes, retrievedAvailableTimeList);
    }

    @Test
    void testGetAvailableBookingTimesForDayAndRepairDurationThreeParameters() {
        //Given
        LocalDate localDate = LocalDate.now().plusDays(1);
        List<Booking> bookingList = List.of(new Booking(BookingStatus.AVAILABLE, localDate, LocalTime.of(9,50), LocalTime.of(11,40), null, null, null));
        when(bookingRepository.findBookingsByDateAndGarageId(localDate, 5L)).thenReturn(bookingList);
        //When
        List<LocalTime> retrievedAvailableTimeList = bookingService.getAvailableBookingTimesForSelectedDayAndRepairDuration(localDate, 50, 5L);
        //Then
        List<LocalTime> expectedTimes = List.of(LocalTime.of(9, 50), LocalTime.of(10,0), LocalTime.of(10,10), LocalTime.of(10, 20),LocalTime.of(10, 30),LocalTime.of(10, 40), LocalTime.of(10, 50));
        assertFalse(retrievedAvailableTimeList.isEmpty());
        assertEquals(7, retrievedAvailableTimeList.size());
        assertEquals(expectedTimes, retrievedAvailableTimeList);
    }

    @Test
    void testBookingSaveWhenNoOpenTimesSet() throws MyEntityNotFoundException, WrongInputDataException {
        //Given
        LocalDate localDate = LocalDate.now().plusDays(1);
        Garage mockedGarage = Mockito.mock(Garage.class);
        List<Booking> bookingList = new ArrayList<>();

        when(garageService.getGarage(anyLong())).thenReturn(mockedGarage);
        when(bookingRepository.findBookingsByDateAndStatusAndGarageId(localDate, BookingStatus.AVAILABLE, 50L)).thenReturn(bookingList);
        when(bookingRepository.save(any())).thenReturn(any());
        //When
        bookingService.saveBooking(localDate, LocalTime.of(8,0), LocalTime.of(15,0), 50L);
        //Then
        verify(bookingRepository, times(1)).save(any());
    }

    @Test
    void testBookingSaveWhenThereAreOpenTimes() throws MyEntityNotFoundException, WrongInputDataException {
        //Given
        LocalDate localDate = LocalDate.now().plusDays(1);
        Garage mockedGarage = Mockito.mock(Garage.class);
        Booking booking = new Booking(BookingStatus.AVAILABLE, localDate, LocalTime.of(8,0), LocalTime.of(15,0), null, null, null);
        booking.setId(1L);
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);

        when(garageService.getGarage(anyLong())).thenReturn(mockedGarage);
        when(bookingRepository.findBookingsByDateAndStatusAndGarageId(localDate, BookingStatus.AVAILABLE, 50L)).thenReturn(bookingList);
        //When & then
        try {
            bookingService.saveBooking(localDate, LocalTime.of(8,0), LocalTime.of(15,0), 50L);
            fail("Expected an WrongInputDataException to be thrown");
        } catch (WrongInputDataException e) {
            assertThat(e.getMessage(), containsString("Work times of given date: " + localDate + ", are already declared"));
        }
        verify(bookingRepository, times(0)).save(any());
    }

    @Test
    void testUpdateBooking() throws MyEntityNotFoundException {
        //Given
        LocalDate localDate = LocalDate.now().plusDays(1);
        LocalDate newLocalDate = LocalDate.now().plusDays(2);
        Booking booking = new Booking(BookingStatus.WAITING_FOR_CUSTOMER, localDate, LocalTime.of(11,0), LocalTime.of(11,30), null, null, null);

        when(bookingRepository.findById(50L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);
        //When
        bookingService.updateBooking(50L, newLocalDate, LocalTime.of(11,20));
        //Then
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void testCreateBooking() throws MyEntityNotFoundException, WrongInputDataException, NoSuchMethodException {
        //Given
        LocalDate localDate = LocalDate.now().plusDays(1);
        int repairDuration = 70;
        Garage mockedGarage = Mockito.mock(Garage.class);
        User user = new User("Firstname", "Lastname", "email", "phonenumber", "username", "password");
        user.setId(1L);
        Car car = new Car(2L, "BMW", "3 Series", 2014, "Sedan", "Diesel", user, new ArrayList<>());
        user.setCarList(List.of(car));
        List<LocalTime> localTimeList = List.of(LocalTime.of(10, 0), LocalTime.of(10,10), LocalTime.of(10, 20),LocalTime.of(10, 30),LocalTime.of(10, 40), LocalTime.of(10, 50), LocalTime.of(11, 0));
        AvailableCarRepair availableCarRepair = new AvailableCarRepair(10L, "testname", "testdescription", BigDecimal.valueOf(50), 30, "BMW", BigDecimal.valueOf(1.2), mockedGarage);
        AvailableCarRepair availableCarRepair2 = new AvailableCarRepair(11L, "testname", "testdescription", BigDecimal.valueOf(70), 40, "AUDI", BigDecimal.valueOf(1.2), mockedGarage);

        when(garageService.getGarage(anyLong())).thenReturn(mockedGarage);
        when(carService.getCar(anyLong())).thenReturn(car);
        when(userService.getUser(anyLong())).thenReturn(user);
        BookingService bookingService = Mockito.spy(new BookingService(bookingRepository, garageService, carRepairService, carService, userService, availableCarRepairService));
        Mockito.doReturn(localTimeList).when(bookingService).getAvailableBookingTimesForSelectedDayAndRepairDuration(localDate, repairDuration, 5L);
        when(bookingRepository.save(any())).thenReturn(any());
        when(availableCarRepairService.getAvailableCarService(10L)).thenReturn(availableCarRepair);
        when(availableCarRepairService.getAvailableCarService(11L)).thenReturn(availableCarRepair2);
        when(userService.saveUser(any(User.class))).thenReturn(Mockito.mock(User.class));
        //When
        bookingService.createBooking(List.of(10L, 11L), localDate, LocalTime.of(10,0), 5L, 2L, repairDuration);
        //Then
        verify(userService, times(1)).saveUser(any(User.class));
        verify(bookingRepository, times(2)).save(any());
    }
}