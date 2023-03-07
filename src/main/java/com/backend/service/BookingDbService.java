package com.backend.service;

import com.backend.domain.Booking;
import com.backend.domain.BookingStatus;
import com.backend.domain.CarService;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingDbService {
    private final BookingRepository bookingRepository;
    private final GarageDbService garageDbService;
    private final CarServiceDbService carServiceDbService;

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public Booking getBooking(Long bookingId) throws MyEntityNotFoundException {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new MyEntityNotFoundException("Booking", bookingId));
    }

    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    public void saveBooking(List<Long> carServiceIdList, LocalDateTime startDate, Long garageId) throws MyEntityNotFoundException {
        Booking booking = new Booking();
        booking.setGarage(garageDbService.getGarage(garageId));
        booking.setStartDate(startDate);
        booking.setCreated(LocalDateTime.now());
        booking.setStatus(BookingStatus.AWAITING);
        List<CarService> carServiceList = new ArrayList<>();
        int repairTimeInMinutes = 0;
        for (Long id : carServiceIdList) {
            CarService carService = carServiceDbService.getCarService(id);
            repairTimeInMinutes += carService.getRepairTimeInMinutes();
            carServiceList.add(carService);
        }
        booking.setEndDate(startDate.plusMinutes(repairTimeInMinutes));
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
