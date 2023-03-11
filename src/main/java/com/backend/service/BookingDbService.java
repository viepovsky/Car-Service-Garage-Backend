package com.backend.service;

import com.backend.domain.*;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.exceptions.WrongInputDataException;
import com.backend.repository.BookingRepository;
import com.backend.repository.CarServiceRepository;
import com.backend.repository.GarageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingDbService {
    private final BookingRepository bookingRepository;
    private final GarageRepository garageRepository;
    private final CarServiceRepository carServiceRepository;

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public List<Booking> getAllBookingsForGivenCustomer(Long customerId) {
        return bookingRepository.findBookingsByCarServiceListCustomerId(customerId);
    }

    public List<LocalTime> getAvailableBookingTimesForSelectedDayAndCarServices(LocalDate date, List<Long> carServiceIdList, Long garageId) throws MyEntityNotFoundException {
        List<CarService> carServiceList = new ArrayList<>();
        for (Long id : carServiceIdList) {
            CarService carService = carServiceRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("CarService", id));
            carServiceList.add(carService);
        }

        int repairDuration = carServiceList.stream().mapToInt(CarService::getRepairTimeInMinutes).sum();

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
            if (currentTime.plusMinutes(repairDuration).isBefore(closeTime) && isAvailable) {
                availableTimeList.add(currentTime);
            }
            currentTime = currentTime.plusMinutes(10);
        }
        return availableTimeList;
    }

    public Booking getBookingOfGivenDateStartHourStatusAndGarageId (LocalDate date, LocalTime startHour, Long garageId, BookingStatus status) {
        return bookingRepository.findBookingByDateAndStartHourAndGarageIdAndStatus(date, startHour, garageId, status);
    }

    public Booking getBooking(Long bookingId) throws MyEntityNotFoundException {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new MyEntityNotFoundException("Booking", bookingId));
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
                    garage
            );
            bookingRepository.save(booking);
        } else {
            List<Long> bookingId = bookingList.stream().map(Booking::getId).toList();
            throw new WrongInputDataException("Work times of given date: " + date + ", are already declared. To change them you need to use PUT request or if there are more than one also DELETE request, check given booking id(s): " + bookingId);
        }
    }

    public void saveBooking(List<Long> carServiceIdList, LocalDate date, LocalTime startHour, Long garageId) throws MyEntityNotFoundException {
        Garage garage = garageRepository.findById(garageId).orElseThrow(() -> new MyEntityNotFoundException("Garage", garageId));
        Booking booking = new Booking();
        booking.setGarage(garage);
        booking.setDate(date);
        booking.setStartHour(startHour);
        booking.setCreated(LocalDateTime.now());
        booking.setStatus(BookingStatus.WAITING_FOR_CUSTOMER);
        List<CarService> carServiceList = new ArrayList<>();
        int totalRepairDuration = 0;
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Long id : carServiceIdList) {
            CarService carService = carServiceRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("CarService", id));
            totalRepairDuration += carService.getRepairTimeInMinutes();
            totalCost = totalCost.add(carService.getCost());
            carServiceList.add(carService);
            carService.setBooking(booking);
            carService.setStatus(ServiceStatus.ASSIGNED);
            carServiceRepository.save(carService);
        }
        booking.setEndHour(startHour.plusMinutes(totalRepairDuration));
        booking.setTotalCost(totalCost);
        booking.setCarServiceList(carServiceList);
        bookingRepository.save(booking);
    }

    public Booking updateBooking(Booking booking) throws MyEntityNotFoundException {
        if (bookingRepository.findById(booking.getId()).isPresent()) {
            return bookingRepository.save(booking);
        } else {
            throw new MyEntityNotFoundException("Booking", booking.getId());
        }
    }

    public void deleteBooking(Long bookingId) throws MyEntityNotFoundException {
        if (bookingRepository.findById(bookingId).isPresent()) {
            bookingRepository.deleteById(bookingId);
        } else {
            throw new MyEntityNotFoundException("Booking", bookingId);
        }
    }
}
