package com.backend.controller;

import com.backend.domain.Booking;
import com.backend.domain.dto.BookingDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.BookingMapper;
import com.backend.service.BookingDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/booking")
@RequiredArgsConstructor
public class BookingController {
    private final BookingDbService bookingDbService;
    private final BookingMapper bookingMapper;

    @GetMapping
    public ResponseEntity<List<BookingDto>> getBookings() {
        List<Booking> bookingList = bookingDbService.getAllBookings();
        return ResponseEntity.ok(bookingMapper.mapToBookingDtoList(bookingList));
    }

    @PostMapping
    public ResponseEntity<Void> createBooking(
            @RequestParam(name = "service-id") List<Long> orderedServiceIdList,
            @RequestParam(name = "start-date") LocalDateTime startDate,
            @RequestParam(name = "garage-id") Long garageId
    ) throws MyEntityNotFoundException {
        bookingDbService.saveBooking(orderedServiceIdList, startDate, garageId);
        return ResponseEntity.ok().build();
    }
}
