package com.viepovsky.booking;

import com.viepovsky.car.Car;
import com.viepovsky.car.CarService;
import com.viepovsky.carrepair.CarRepair;
import com.viepovsky.carrepair.CarRepairService;
import com.viepovsky.carrepair.RepairStatus;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.exceptions.WrongInputDataException;
import com.viepovsky.garage.Garage;
import com.viepovsky.garage.GarageService;
import com.viepovsky.garage.availablerepair.AvailableCarRepair;
import com.viepovsky.garage.availablerepair.AvailableCarRepairService;
import com.viepovsky.user.User;
import com.viepovsky.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingService.class);
    private final BookingRepository repository;
    private final GarageService garageService;
    private final CarRepairService carRepairService;
    private final CarService carService;
    private final UserService userService;
    private final AvailableCarRepairService availableCarRepairService;

    public List<Booking> getAllBookings() {
        return repository.findAll();
    }

    public List<Booking> getAllBookingsForGivenUser(String username) throws MyEntityNotFoundException {
        User user = userService.getUser(username);
        return repository.findBookingsByCarRepairListUserId(user.getId());
    }

    public List<Booking> getBookingsForGivenDateAndGarageId(LocalDate date, Long garageId) throws MyEntityNotFoundException {
        return repository.findBookingsByDateAndGarageId(date, garageId);
    }

    private Booking getBooking(Long id) throws MyEntityNotFoundException {
        return repository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("Booking" + id));
    }

    public List<LocalTime> getAvailableBookingTimesForSelectedDayAndRepairDuration(LocalDate date, Long serviceId) throws MyEntityNotFoundException {
        var carService = carRepairService.getCarRepair(serviceId);
        var reservedBooking = getBooking(carService.getBooking().getId());
        int repairDuration = reservedBooking.getCarRepairList().stream().mapToInt(CarRepair::getRepairTimeInMinutes).sum();
        Long garageId = reservedBooking.getGarage().getId();

        LOGGER.info("Given parameters to get available times, date: " + date + ", total repair time: " + repairDuration + ", garage id: " + garageId);
        List<Booking> allBookingsForDay = repository.findBookingsByDateAndGarageId(date, garageId);
        allBookingsForDay.remove(reservedBooking);

        List<LocalTime> availableBookingTimes = checkAvailableBookingTimes(allBookingsForDay, date, repairDuration);
        availableBookingTimes.remove(reservedBooking.getStartHour());
        return availableBookingTimes;
    }

    public List<LocalTime> getAvailableBookingTimesForSelectedDayAndRepairDuration(LocalDate date, int repairDuration, Long garageId) {
        LOGGER.info("Given parameters to get available times, date: " + date + ", total repair time: " + repairDuration + ", garage id: " + garageId);
        List<Booking> bookingList = repository.findBookingsByDateAndGarageId(date, garageId);
        return checkAvailableBookingTimes(bookingList, date, repairDuration);
    }

    private List<LocalTime> checkAvailableBookingTimes(List<Booking> bookingList, LocalDate date, int repairDuration) {
        if (!isGarageWorkingHoursPresent(bookingList)) {
            return new ArrayList<>();
        }

        Booking garageWorkTime = bookingList.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.AVAILABLE)
                .findFirst()
                .orElse(null);
        if (!isGarageWorkTimePresent(garageWorkTime)) {
            return new ArrayList<>();
        }

        LocalTime garageOpenTime = garageWorkTime.getStartHour();
        LocalTime garageCloseTime = garageWorkTime.getEndHour();
        if (isOpenTimeBeforeNow(date, garageOpenTime)) {
            if (isCloseTimeBeforeNow(garageCloseTime)) {
                return new ArrayList<>();
            }
            garageOpenTime = roundUpTimeToNearest10Minutes();
        }

        List<Booking> unavailableBookingTimeList = bookingList.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.UNAVAILABLE || booking.getStatus() == BookingStatus.WAITING_FOR_CUSTOMER)
                .toList();

        return getAvailableTimesForBooking(repairDuration, garageCloseTime, unavailableBookingTimeList, garageOpenTime);
    }

    private boolean isGarageWorkingHoursPresent(List<Booking> bookingList) {
        return bookingList.size() != 0;
    }

    private boolean isGarageWorkTimePresent(Booking garageWorkTime) {
        return garageWorkTime != null;
    }

    private boolean isOpenTimeBeforeNow(LocalDate date, LocalTime openTime) {
        return (date.isEqual(LocalDate.now()) && LocalTime.now().isAfter(openTime));
    }

    private LocalTime roundUpTimeToNearest10Minutes() {
        LocalTime timeNow = LocalTime.now();
        int minutes = timeNow.getMinute() + timeNow.getHour() * 60;
        minutes = ((minutes + 10) / 10) * 10;
        return LocalTime.of(minutes / 60, minutes % 60);
    }

    private boolean isCloseTimeBeforeNow(LocalTime closeTime) {
        return closeTime.isBefore(LocalTime.now());
    }

    private List<LocalTime> getAvailableTimesForBooking(int repairDuration, LocalTime closeTime, List<Booking> unavailableBookingTimeList, LocalTime currentTime) {
        List<LocalTime> availableBookingTimes = new ArrayList<>();
        while (!currentTime.plusMinutes(repairDuration).isAfter(closeTime)) {
            boolean isAvailable = true;
            for (Booking booking : unavailableBookingTimeList) {
                if (currentTime.plusMinutes(repairDuration).isAfter(booking.getStartHour()) && booking.getEndHour().isAfter(currentTime)) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                availableBookingTimes.add(currentTime);
            }
            currentTime = currentTime.plusMinutes(10);
        }
        return availableBookingTimes;
    }

    public void saveBooking(LocalDate date, LocalTime startHour, LocalTime endHour, Long garageId) throws WrongInputDataException, MyEntityNotFoundException {
        Garage garage = garageService.getGarage(garageId);
        List<Booking> bookingList = repository.findBookingsByDateAndStatusAndGarageId(date, BookingStatus.AVAILABLE, garageId);
        if (!isGarageWorkingHoursPresent(bookingList)) {
            Booking booking = createGarageWorkingHoursBooking(date, startHour, endHour, garage);
            repository.save(booking);
        } else {
            List<Long> bookingIdList = bookingList.stream().map(Booking::getId).toList();
            throw new WrongInputDataException("Work times of given date: " + date + ", are already declared. To change them you need to use PUT request or if there are more than one also DELETE request, check given booking id(s): " + bookingIdList);
        }
    }

    private Booking createGarageWorkingHoursBooking(LocalDate date, LocalTime startHour, LocalTime endHour, Garage garage) {
        return new Booking(
                BookingStatus.AVAILABLE,
                date,
                startHour,
                endHour,
                BigDecimal.ZERO,
                new ArrayList<>(),
                garage
        );
    }

    public void updateBooking(Long bookingId, LocalDate date, LocalTime startHour) throws MyEntityNotFoundException {
        Booking booking = repository.findById(bookingId).orElseThrow(() -> new MyEntityNotFoundException("Booking" + bookingId));
        booking.setDate(date);
        int repairTime = (int) Duration.between(booking.getStartHour(), booking.getEndHour()).toMinutes();
        booking.setStartHour(startHour);
        booking.setEndHour(startHour.plusMinutes(repairTime));
        LOGGER.info("Updated booking with values, date: " + date + ", time: " + startHour);
        repository.save(booking);
    }

    public void createBooking(List<Long> selectedServiceIdList, LocalDate date, LocalTime startHour, Long garageId, Long carId, int repairDuration) throws MyEntityNotFoundException, WrongInputDataException {
        Garage garage = garageService.getGarage(garageId);
        Car car = carService.getCar(carId);
        User user = userService.getUser(car.getUser().getId());
        List<LocalTime> availableBookingTimes = getAvailableBookingTimesForSelectedDayAndRepairDuration(date, repairDuration, garageId);
        if (availableBookingTimes.contains(startHour)) {
            Booking booking = createCarRepairBooking(date, startHour, repairDuration, garage);
            repository.save(booking);
            saveBookingAndServicesForGivenCarAndUser(selectedServiceIdList, car, user, booking);
        } else {
            throw new WrongInputDataException("Given time: " + startHour + " is no longer available. Choose another date.");
        }
    }

    private Booking createCarRepairBooking(LocalDate date, LocalTime startHour, int repairDuration, Garage garage) {
        return new Booking(
                BookingStatus.WAITING_FOR_CUSTOMER,
                date,
                startHour,
                startHour.plusMinutes(repairDuration),
                BigDecimal.ZERO,
                new ArrayList<>(),
                garage
        );
    }

    private void saveBookingAndServicesForGivenCarAndUser(List<Long> selectedServiceIdList, Car car, User user, Booking booking) throws MyEntityNotFoundException {
        List<AvailableCarRepair> availableCarRepairList = new ArrayList<>();
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Long id : selectedServiceIdList) {
            AvailableCarRepair availableCarRepair = new AvailableCarRepair(availableCarRepairService.getAvailableCarService(id));
            if (availableCarRepair.getPremiumMakes().toLowerCase().contains(car.getMake().toLowerCase())) {
                BigDecimal multipliedCost = availableCarRepair.getCost();
                multipliedCost = multipliedCost.multiply(availableCarRepair.getMakeMultiplier());
                availableCarRepair.setCost(multipliedCost);
            }
            totalCost = totalCost.add(availableCarRepair.getCost());
            availableCarRepairList.add(availableCarRepair);
        }

        List<CarRepair> carRepairList = availableCarRepairList.stream()
                .map(selectedService -> new CarRepair(
                        selectedService.getName(),
                        selectedService.getDescription(),
                        selectedService.getCost(),
                        selectedService.getRepairTimeInMinutes(),
                        car,
                        user,
                        booking,
                        RepairStatus.AWAITING
                ))
                .toList();

        user.getCarList().stream()
                .filter(servicedCar -> Objects.equals(servicedCar.getId(), car.getId()))
                .findFirst()
                .ifPresent(servicedCar -> servicedCar.getCarServicesList().addAll(carRepairList));
        user.getServicesList().addAll(carRepairList);
        userService.saveUser(user);
        booking.getCarRepairList().addAll(carRepairList);
        booking.setTotalCost(totalCost);
        repository.save(booking);
    }

    public void save(Booking booking) {
        repository.save(booking);
    }

    public void delete(Booking booking) {
        repository.delete(booking);
    }
}
