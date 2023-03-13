package com.backend;

import com.backend.domain.*;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.exceptions.WrongInputDataException;
import com.backend.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@DisplayName("Booking Process Db Services Integration Test Suite")
public class BookingProcessDbServiceIntegrationTestSuite {
    @Autowired
    private GarageDbService garageDbService;

    @Autowired
    private GarageWorkTimeDbService garageWorkTimeDbService;

    @Autowired
    private AvailableCarServiceDbService availableCarServiceDbService;

    @Autowired
    private UserDbService userDbService;

    @Autowired
    private CarDbService carDbService;

    @Autowired
    private BookingDbService bookingDbService;

    @Autowired
    private CarServiceDbService carServiceDbService;

    @Test
    public void testBookingProcess() throws MyEntityNotFoundException, WrongInputDataException {
        //Given
        initGarageWithWorkTimesAndAvailableCarServices();
        initUser();
        initCar();
        initAvailableBookings();
        initAddingCarService();
        Car car = carDbService.getAllCars().get(0);
        List<CarService> carServiceList = carServiceDbService.getAllCarServiceWithGivenCarIdAndNotAssignedStatus(car.getId());
        List<Long> carServiceIdList = carServiceList.stream().map(CarService::getId).toList();
        Garage garage = garageDbService.getAllGarages().get(0);
        LocalDate date = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        List<LocalTime> availableTimeToBookList = bookingDbService.getAvailableBookingTimesForSelectedDayAndCarServices(date, carServiceIdList, garage.getId());
        LocalTime startHour = availableTimeToBookList.get(1);
        //When
        bookingDbService.saveBooking(carServiceIdList, date, startHour, garage.getId());
        User user = userDbService.getAllUsers().get(0);
        Booking booking = bookingDbService.getBookingOfGivenDateStartHourStatusAndGarageId(date, startHour, garage.getId(), BookingStatus.WAITING_FOR_CUSTOMER);
        //Then
        assertAll("Getting booking values through car entity, from user",
                () -> assertEquals(BookingStatus.WAITING_FOR_CUSTOMER, user.getCarList().get(0).getCarServicesList().get(0).getBooking().getStatus()),
                () -> assertEquals(LocalTime.of(7,10), user.getCarList().get(0).getCarServicesList().get(0).getBooking().getStartHour()),
                () -> assertEquals(BigDecimal.valueOf(590), user.getCarList().get(0).getCarServicesList().get(0).getBooking().getTotalCost())
                );
        assertAll("Getting booking values directly through car service, from user",
                () -> assertEquals(BookingStatus.WAITING_FOR_CUSTOMER, user.getServicesList().get(0).getBooking().getStatus()),
                () -> assertEquals(LocalTime.of(7,10), user.getServicesList().get(0).getBooking().getStartHour()),
                () -> assertEquals(BigDecimal.valueOf(590), user.getServicesList().get(0).getBooking().getTotalCost())
        );
        assertAll("Getting user and car values directly through car service, from booking",
                () -> assertEquals("test@test.com", booking.getCarServiceList().get(0).getUser().getEmail()),
                () -> assertEquals("BMW", booking.getCarServiceList().get(0).getCar().getMake())
        );
    }

    @Test
    public void testInitMethods() throws MyEntityNotFoundException, WrongInputDataException {
        //Given
        initGarageWithWorkTimesAndAvailableCarServices();
        initUser();
        initCar();
        initAvailableBookings();
        initAddingCarService();
        //When
        List<GarageWorkTime> garageWorkTimeList = garageWorkTimeDbService.getAllGarageWorkTimes();
        List<AvailableCarService> availableCarServiceList = availableCarServiceDbService.getAllAvailableCarService();

        List<User> userList = userDbService.getAllUsers();

        List<Car> carList = carDbService.getAllCars();

        List<Booking> bookingList = bookingDbService.getAllBookings();

        Car car = carDbService.getAllCars().get(0);
        List<CarService> carServiceList = carServiceDbService.getAllCarServiceWithGivenCarIdAndNotAssignedStatus(car.getId());
        List<Long> carServiceIdList = carServiceList.stream().map(CarService::getId).toList();
        Garage garage = garageDbService.getAllGarages().get(0);
        LocalDate date = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        List<LocalTime> availableTimeToBookList = bookingDbService.getAvailableBookingTimesForSelectedDayAndCarServices(date, carServiceIdList, garage.getId());
        //Then
        assertAll("Garage with work times and car services",
                () -> assertEquals("Monday", garageWorkTimeList.get(0).getDay().getDayName()),
                () -> assertEquals(LocalTime.of(0,0), garageWorkTimeList.get(6).getEndHour()),
                () -> assertEquals("Garage test name", garageWorkTimeList.get(0).getGarage().getName()),
                () -> assertEquals(5, availableCarServiceList.size()),
                () -> assertEquals("Test car service #1", availableCarServiceList.get(0).getName()),
                () -> assertEquals("Garage test name", availableCarServiceList.get(0).getGarage().getName())
                );

        assertAll("User",
                () -> assertEquals("Test name", userList.get(0).getFirstName()),
                () -> assertEquals("Testusername", userList.get(0).getUsername()),
                () -> assertEquals("Testpassword123", userList.get(0).getPassword()),
                () -> assertEquals("test@test.com", userList.get(0).getEmail())
        );

        assertAll("Car",
                () -> assertEquals(2, carList.size()),
                () -> assertEquals("Test name", carList.get(0).getUser().getFirstName()),
                () -> assertEquals("diesel", carList.get(0).getEngine())
                );

        assertAll("Available bookings",
                () -> assertEquals(365, bookingList.size()),
                () -> assertEquals(BookingStatus.AVAILABLE, bookingList.get(0).getStatus())
                );
        assertAll("Car service added, possible bookings check",
                () -> assertEquals(3, carServiceList.size()),
                () -> assertEquals(LocalTime.of(7,0), availableTimeToBookList.get(0)),
                () -> assertEquals(32, availableTimeToBookList.size())
        );
    }

    private void initGarageWithWorkTimesAndAvailableCarServices() throws MyEntityNotFoundException {
        Garage garage = new Garage("Garage test name", "Garage test description");
        garageDbService.saveGarage(garage);
        garage = garageDbService.getAllGarages().get(0);

        GarageWorkTime workDay1 = new GarageWorkTime(WorkDays.MONDAY.toString(), LocalTime.of(7,0),LocalTime.of(15,0));
        GarageWorkTime workDay2 = new GarageWorkTime(WorkDays.TUESDAY.toString(), LocalTime.of(7,0),LocalTime.of(15,0));
        GarageWorkTime workDay3 = new GarageWorkTime(WorkDays.WEDNESDAY.toString(), LocalTime.of(7,0),LocalTime.of(15,0));
        GarageWorkTime workDay4 = new GarageWorkTime(WorkDays.THURSDAY.toString(), LocalTime.of(7,0),LocalTime.of(15,0));
        GarageWorkTime workDay5 = new GarageWorkTime(WorkDays.FRIDAY.toString(), LocalTime.of(7,0),LocalTime.of(15,0));
        GarageWorkTime workDay6 = new GarageWorkTime(WorkDays.SATURDAY.toString(), LocalTime.of(8,0),LocalTime.of(12,0));
        GarageWorkTime workDay7 = new GarageWorkTime(WorkDays.SUNDAY.toString(), LocalTime.of(0,0),LocalTime.of(0,0));
        garageWorkTimeDbService.saveGarageWorkTime(workDay1, garage.getId());
        garageWorkTimeDbService.saveGarageWorkTime(workDay2, garage.getId());
        garageWorkTimeDbService.saveGarageWorkTime(workDay3, garage.getId());
        garageWorkTimeDbService.saveGarageWorkTime(workDay4, garage.getId());
        garageWorkTimeDbService.saveGarageWorkTime(workDay5, garage.getId());
        garageWorkTimeDbService.saveGarageWorkTime(workDay6, garage.getId());
        garageWorkTimeDbService.saveGarageWorkTime(workDay7, garage.getId());

        AvailableCarService availableCarService1 = new AvailableCarService( "Test car service #1", "Test description car service #1", BigDecimal.valueOf(50), 40, "BMW, AUDI, MERCEDES", BigDecimal.valueOf(1.2), null);
        AvailableCarService availableCarService2 = new AvailableCarService( "Test car service #2", "Test description car service #2", BigDecimal.valueOf(170), 80, "BMW, AUDI, MERCEDES", BigDecimal.valueOf(1.2), null);
        AvailableCarService availableCarService3 = new AvailableCarService( "Test car service #3", "Test description car service #3", BigDecimal.valueOf(500), 110, "BMW, AUDI, MERCEDES", BigDecimal.valueOf(1.2), null);
        AvailableCarService availableCarService4 = new AvailableCarService( "Test car service #4", "Test description car service #4", BigDecimal.valueOf(40), 15, "BMW, AUDI, MERCEDES", BigDecimal.valueOf(1.2), null);
        AvailableCarService availableCarService5 = new AvailableCarService( "Test car service #5", "Test description car service #5", BigDecimal.valueOf(55), 30, "BMW, AUDI, MERCEDES", BigDecimal.valueOf(1.2), null);
        availableCarServiceDbService.saveAvailableCarService(availableCarService1, garage.getId());
        availableCarServiceDbService.saveAvailableCarService(availableCarService2, garage.getId());
        availableCarServiceDbService.saveAvailableCarService(availableCarService3, garage.getId());
        availableCarServiceDbService.saveAvailableCarService(availableCarService4, garage.getId());
        availableCarServiceDbService.saveAvailableCarService(availableCarService5, garage.getId());
    }

    private void initUser() throws MyEntityNotFoundException {
        User user = new User("Test name", "Test lastname", "test@test.com", "+48777777777", "Testusername", "Testpassword123", UserRole.USER, LocalDateTime.now(), new ArrayList<>(), new ArrayList<>());
        userDbService.saveUser(user);
    }

    private void initCar() throws MyEntityNotFoundException {
        Car car = new Car("BMW","Series 3", "Sedan", 2020, "diesel");
        User user = userDbService.getAllUsers().get(0);
        carDbService.saveCar(car, user.getId());
        car = new Car("AUDI","A6", "Sedan", 2020, "diesel");
        carDbService.saveCar(car, user.getId());
    }

    private void initAvailableBookings() throws WrongInputDataException, MyEntityNotFoundException {
        Garage garage = garageDbService.getAllGarages().get(0);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(365);
        List<GarageWorkTime> garageWorkTimeList = garageWorkTimeDbService.getAllGarageWorkTimes();
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            LocalDate finalDateForLoop = date;
            GarageWorkTime garageWorkTime = garageWorkTimeList.stream()
                    .filter(workTime -> workTime.getDay().toString().equals(finalDateForLoop.getDayOfWeek().toString()))
                    .findFirst()
                    .orElseThrow(() -> new WrongInputDataException("GarageWorkTime not found for day: " + finalDateForLoop.getDayOfWeek().toString()));
            bookingDbService.saveBooking(date, garageWorkTime.getStartHour(), garageWorkTime.getEndHour(), garage.getId());
        }
    }

    private void initAddingCarService() throws MyEntityNotFoundException {
        List<AvailableCarService> availableCarServiceList = availableCarServiceDbService.getAllAvailableCarService();
        List<Long> carServicesIdList = new ArrayList<>();
        carServicesIdList.add(availableCarServiceList.get(0).getId());
        carServicesIdList.add(availableCarServiceList.get(2).getId());
        carServicesIdList.add(availableCarServiceList.get(3).getId());
        Car car = carDbService.getAllCars().get(0);
        carServiceDbService.saveCarService(carServicesIdList, car.getId());
    }
}
