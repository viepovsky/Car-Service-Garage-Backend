package com.backend.service;

import com.backend.domain.*;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.exceptions.WrongInputDataException;
import com.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingDbService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingDbService.class);
    private final BookingRepository bookingRepository;
    private final GarageRepository garageRepository;
    private final CarServiceRepository carServiceRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final AvailableCarServiceRepository availableCarServiceRepository;

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public List<Booking> getAllBookingsForGivenUser(String username) throws MyEntityNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new MyEntityNotFoundException("Username: " + username));
        return bookingRepository.findBookingsByCarServiceListUserId(user.getId());
    }

    public List<LocalTime> getAvailableBookingTimesForSelectedDayAndRepairDuration(LocalDate date, Long serviceId) throws MyEntityNotFoundException {
        CarService carService = carServiceRepository.findById(serviceId).orElseThrow(() -> new MyEntityNotFoundException("CarService" + serviceId));
        Booking bookedService = bookingRepository.findById(carService.getBooking().getId()).orElseThrow(() -> new MyEntityNotFoundException("Booking" + carService.getBooking().getId()));
        int repairDuration = bookedService.getCarServiceList().stream().mapToInt(CarService::getRepairTimeInMinutes).sum();
        Long garageId = bookedService.getGarage().getId();

        LOGGER.info("Given parameters to get available times, date: " + date + ", total repair time: " + repairDuration + ", garage id: " + garageId);
        List<Booking> bookingList = bookingRepository.findBookingsByDateAndGarageId(date, garageId);
        bookingList.remove(bookedService);
        if (bookingList.size() == 0) {
            return new ArrayList<>();
        }

        Booking garageWorkTime = bookingList.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.AVAILABLE)
                .findFirst()
                .orElse(null);
        if (garageWorkTime == null) {
            return  new ArrayList<>();
        }

        LocalTime openTime = garageWorkTime.getStartHour();
        if (date.isEqual(LocalDate.now()) && LocalTime.now().isAfter(openTime)) {
            int minutes = LocalTime.now().getMinute() + LocalTime.now().getHour() * 60;
            minutes = ((minutes+10) / 10) * 10;
            openTime = LocalTime.of(minutes / 60, minutes % 60);
        }
        LocalTime closeTime = garageWorkTime.getEndHour();

        List<Booking> unavailableBookingTimeList = bookingList.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.UNAVAILABLE || booking.getStatus() == BookingStatus.WAITING_FOR_CUSTOMER)
                .toList();

        List<LocalTime> availableTimeList = new ArrayList<>();
        LocalTime currentTime = openTime;

        while (!currentTime.plusMinutes(repairDuration).isAfter(closeTime)) {
            boolean isAvailable = true;
            for (Booking booking : unavailableBookingTimeList) {
                if (currentTime.plusMinutes(repairDuration).isAfter(booking.getStartHour()) && booking.getEndHour().isAfter(currentTime)) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                availableTimeList.add(currentTime);
            }
            currentTime = currentTime.plusMinutes(10);
        }
        availableTimeList.remove(bookedService.getStartHour());
        return availableTimeList;
    }

    public List<LocalTime> getAvailableBookingTimesForSelectedDayAndRepairDuration(LocalDate date, int repairDuration, Long garageId) {
        LOGGER.info("Given parameters to get available times, date: " + date + ", total repair time: " + repairDuration + ", garage id: " + garageId);
        List<Booking> bookingList = bookingRepository.findBookingsByDateAndGarageId(date, garageId);
        if (bookingList.size() == 0) {
            return new ArrayList<>();
        }

        Booking garageWorkTime = bookingList.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.AVAILABLE)
                .findFirst()
                .orElse(null);
        if (garageWorkTime == null) {
            return  new ArrayList<>();
        }

        LocalTime openTime = garageWorkTime.getStartHour();
        if (date.isEqual(LocalDate.now()) && LocalTime.now().isAfter(openTime)) {
            int minutes = LocalTime.now().getMinute() + LocalTime.now().getHour() * 60;
            minutes = ((minutes+10) / 10) * 10;
            openTime = LocalTime.of(minutes / 60, minutes % 60);
        }
        LocalTime closeTime = garageWorkTime.getEndHour();

        List<Booking> unavailableBookingTimeList = bookingList.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.UNAVAILABLE || booking.getStatus() == BookingStatus.WAITING_FOR_CUSTOMER)
                .toList();

        List<LocalTime> availableTimeList = new ArrayList<>();
        LocalTime currentTime = openTime;

        while (!currentTime.plusMinutes(repairDuration).isAfter(closeTime)) {
            boolean isAvailable = true;
            for (Booking booking : unavailableBookingTimeList) {
                if (currentTime.plusMinutes(repairDuration).isAfter(booking.getStartHour()) && booking.getEndHour().isAfter(currentTime)) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                availableTimeList.add(currentTime);
            }
            currentTime = currentTime.plusMinutes(10);
        }
        return availableTimeList;
    }

    public void saveBooking(LocalDate date, LocalTime startHour, LocalTime endHour, Long garageId) throws WrongInputDataException, MyEntityNotFoundException {
        Garage garage = garageRepository.findById(garageId).orElseThrow(() -> new MyEntityNotFoundException("Garage", garageId));
        List<Booking> bookingList = bookingRepository.findBookingsByDateAndStatusAndGarageId(date, BookingStatus.AVAILABLE, garageId);
        if (bookingList.size() == 0) {
            Booking booking = new Booking(
                    BookingStatus.AVAILABLE,
                    date,
                    startHour,
                    endHour,
                    LocalDateTime.now(),
                    BigDecimal.ZERO,
                    new ArrayList<>(),
                    garage
            );
            bookingRepository.save(booking);
        } else {
            List<Long> bookingId = bookingList.stream().map(Booking::getId).toList();
            throw new WrongInputDataException("Work times of given date: " + date + ", are already declared. To change them you need to use PUT request or if there are more than one also DELETE request, check given booking id(s): " + bookingId);
        }
    }

    public void updateBooking(Long bookingId, LocalDate date, LocalTime startHour) throws MyEntityNotFoundException {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new MyEntityNotFoundException("Booking" + bookingId));
        booking.setDate(date);
        int repairTime = (int)Duration.between(booking.getStartHour(), booking.getEndHour()).toMinutes();
        booking.setStartHour(startHour);
        booking.setEndHour(startHour.plusMinutes(repairTime));
        LOGGER.info("Updated booking with values, date: " + date + ", time: " + startHour);
        bookingRepository.save(booking);
    }

    public void createBooking(List<Long> selectedServiceIdList, LocalDate date, LocalTime startHour, Long garageId, Long carId, int repairDuration) throws MyEntityNotFoundException, WrongInputDataException {
        Garage garage = garageRepository.findById(garageId).orElseThrow(() -> new MyEntityNotFoundException("Garage", garageId));
        Car car = carRepository.findById(carId).orElseThrow(() -> new MyEntityNotFoundException("Car", carId));
        User user = userRepository.findById(car.getUser().getId()).orElseThrow(() -> new MyEntityNotFoundException("User", car.getUser().getId()));
        List<LocalTime> localTimeList = getAvailableBookingTimesForSelectedDayAndRepairDuration(date, repairDuration, garageId);
        if (localTimeList.contains(startHour)) {
            Booking booking = new Booking(
                    BookingStatus.WAITING_FOR_CUSTOMER,
                    date,
                    startHour,
                    startHour.plusMinutes(repairDuration),
                    LocalDateTime.now(),
                    BigDecimal.ZERO,
                    new ArrayList<>(),
                    garage
            );
            bookingRepository.save(booking);
            saveBookingAndServicesForGivenCarAndUser(selectedServiceIdList, car, user, booking);
        } else {
            throw new WrongInputDataException("Given time: " + startHour + " is no longer available. Choose another date.");
        }
    }

    private void saveBookingAndServicesForGivenCarAndUser(List<Long> selectedServiceIdList, Car car, User user, Booking booking) throws MyEntityNotFoundException {
        List<AvailableCarService> availableCarServiceList = new ArrayList<>();
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Long id : selectedServiceIdList) {
            AvailableCarService availableCarService = new AvailableCarService(availableCarServiceRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("AvailableCarService", id)));
            if (availableCarService.getPremiumMakes().toLowerCase().contains(car.getMake().toLowerCase())) {
                BigDecimal multipliedCost = availableCarService.getCost();
                multipliedCost = multipliedCost.multiply(availableCarService.getMakeMultiplier());
                availableCarService.setCost(multipliedCost);
            }
            totalCost = totalCost.add(availableCarService.getCost());
            availableCarServiceList.add(availableCarService);
        }

        List<CarService> carServiceList = availableCarServiceList.stream()
                .map(selectedService -> new CarService(
                        selectedService.getName(),
                        selectedService.getDescription(),
                        selectedService.getCost(),
                        selectedService.getRepairTimeInMinutes(),
                        car,
                        user,
                        booking,
                        ServiceStatus.AWAITING
                    ))
                .toList();

        user.getCarList().stream()
                .filter(servicedCar -> Objects.equals(servicedCar.getId(), car.getId()))
                .findFirst()
                .ifPresent(servicedCar -> servicedCar.getCarServicesList().addAll(carServiceList));
        user.getServicesList().addAll(carServiceList);
        userRepository.save(user);
        booking.getCarServiceList().addAll(carServiceList);
        booking.setTotalCost(totalCost);
        bookingRepository.save(booking);
    }
}
