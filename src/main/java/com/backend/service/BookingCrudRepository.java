package com.backend.service;

import com.backend.domain.Booking;
import com.backend.exceptions.BookingNotFoundException;
import com.backend.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingCrudRepository {
    private BookingRepository bookingRepository;

    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public Booking getBooking(Long bookingId) throws BookingNotFoundException {
        return bookingRepository.findById(bookingId).orElseThrow(BookingNotFoundException::new);
    }

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public Booking updateBooking(Booking booking) throws BookingNotFoundException {
        if (bookingRepository.findById(booking.getId()).isPresent()) {
            return bookingRepository.save(booking);
        } else {
            throw new BookingNotFoundException();
        }
    }

    public void deleteBooking(Long bookingId) throws BookingNotFoundException {
        if (bookingRepository.findById(bookingId).isPresent()) {
            bookingRepository.deleteById(bookingId);
        } else {
            throw new BookingNotFoundException();
        }
    }
}
