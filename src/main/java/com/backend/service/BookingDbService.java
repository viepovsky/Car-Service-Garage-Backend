package com.backend.service;

import com.backend.domain.Booking;
import com.backend.domain.BookingStatus;
import com.backend.domain.CarService;
import com.backend.domain.Garage;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.exceptions.WrongInputAdminException;
import com.backend.repository.BookingRepository;
import com.backend.repository.CarServiceRepository;
import com.backend.repository.GarageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookingDbService {
    private final BookingRepository bookingRepository;
    private final GarageRepository garageRepository;
    private final CarServiceRepository carServiceRepository;

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public List<LocalTime> getAvailableBookingTimesForSelectedDayAndCarServices(LocalDate date, List<CarService> carServiceList, Long garageId) throws WrongInputAdminException {
        if (carServiceList.isEmpty()) {
            throw new WrongInputAdminException("Given CarService list is empty");
        }

        int repairTimeInMinutes = carServiceList.stream().mapToInt(CarService::getRepairTimeInMinutes).sum();

        List<Booking> bookingList = bookingRepository.findBookingsByDateAndGarageId(date, garageId);
        if (bookingList.size() == 0) {
            return new ArrayList<>();
        }

        Booking workTimeBookingRecord = bookingList.stream()
                        .filter(booking -> booking.getStatus() == BookingStatus.AVAILABLE)
                        .findFirst()
                        .orElse(null);
        if (workTimeBookingRecord == null) {
            return  new ArrayList<>();
        }

        LocalTime openTime = workTimeBookingRecord.getStartHour();
        LocalTime closeTime = workTimeBookingRecord.getEndHour();

        List<Booking> unavailableBookingTimeList = bookingList.stream()
                .filter(booking -> booking.getStatus() == BookingStatus.UNAVAILABLE || booking.getStatus() == BookingStatus.WAITING_FOR_CUSTOMER)
                .toList();

        List<LocalTime> availableTimeList = new ArrayList<>();
        LocalTime currentTime = openTime;

        while (!currentTime.plusMinutes(repairTimeInMinutes).isAfter(closeTime)) {
            boolean isAvailable = true;
            for (Booking booking : unavailableBookingTimeList) {
                if (booking.getStartHour().isBefore(currentTime) && booking.getEndHour().isAfter(currentTime)){
                    isAvailable = false;
                    break;
                } else if (currentTime.plusMinutes(repairTimeInMinutes).isAfter(booking.getStartHour())) {
                    isAvailable = false;
                    break;
                }
            }
            if (currentTime.plusMinutes(repairTimeInMinutes).isBefore(closeTime) && isAvailable) {
                availableTimeList.add(currentTime);
            }
            currentTime = currentTime.plusMinutes(10);
        }
        return availableTimeList;
    }

    public Booking getBooking(Long bookingId) throws MyEntityNotFoundException {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new MyEntityNotFoundException("Booking", bookingId));
    }

    public void saveBooking(LocalDate date, LocalTime startHour, LocalTime endHour, Long garageId) throws WrongInputAdminException, MyEntityNotFoundException {
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
            List<Long> bookingId = new ArrayList<>();
            for (Booking booking : bookingList) {
                if (booking.getTotalCost() == null && booking.getCarServiceList() == null) {
                    bookingId.add(booking.getId());
                }
            }
            if (bookingId.size() > 0){
                throw new WrongInputAdminException("Work times of given date: " + date + ", are already declared. To change them you need to use PUT request or if there are more than one also DELETE request, check given booking id(s): " + bookingId);
            }
            throw new WrongInputAdminException("Work times of given date: " + date + ", are not declared. But there are bookings for given date, check bookings of given date.");
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
        int repairTimeInMinutes = 0;
        for (Long id : carServiceIdList) {
            CarService carService = carServiceRepository.findById(id).orElseThrow(() -> new MyEntityNotFoundException("CarService", id));
            repairTimeInMinutes += carService.getRepairTimeInMinutes();
            carServiceList.add(carService);
        }
        booking.setEndHour(startHour.plusMinutes(repairTimeInMinutes));
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
