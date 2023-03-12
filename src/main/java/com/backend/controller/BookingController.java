package com.backend.controller;

import com.backend.config.AdminConfig;
import com.backend.domain.Booking;
import com.backend.domain.dto.BookingDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.exceptions.WrongInputDataException;
import com.backend.mapper.BookingMapper;
import com.backend.service.BookingDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {
    private final BookingDbService bookingDbService;
    private final BookingMapper bookingMapper;
    private final AdminConfig adminConfig;

    @GetMapping
    public ResponseEntity<List<BookingDto>> getBookings(@RequestParam(name = "customer-id") @NotNull Long customerId) {
        List<Booking> bookingList = bookingDbService.getAllBookingsForGivenCustomer(customerId);
        return ResponseEntity.ok(bookingMapper.mapToBookingDtoList(bookingList));
    }

    @GetMapping(path = "/available-times")
    public ResponseEntity<List<LocalTime>> getAvailableBookingTimes(
            @RequestParam(name = "date") @NotNull LocalDate date,
            @RequestParam(name = "service-id") @NotEmpty List<Long> carServiceList,
            @RequestParam(name = "garage-id") @NotNull Long garageId
    ) throws MyEntityNotFoundException {
        return ResponseEntity.ok(bookingDbService.getAvailableBookingTimesForSelectedDayAndCarServices(date, carServiceList, garageId));
    }

    @PostMapping
    public ResponseEntity<Void> createBookingForCustomerCar(
            @RequestParam(name = "service-id") @NotEmpty List<Long> orderedServiceIdList,
            @RequestParam(name = "date") @NotNull LocalDate date,
            @RequestParam(name = "start-hour") @NotNull LocalTime startHour,
            @RequestParam(name = "garage-id") @NotNull Long garageId
    ) throws MyEntityNotFoundException {
        bookingDbService.saveBooking(orderedServiceIdList, date, startHour, garageId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/admin")
    public ResponseEntity<String> createAvailableBooking(
            @RequestParam(name = "date") @NotNull LocalDate date,
            @RequestParam(name = "start-hour") @NotNull LocalTime startHour,
            @RequestParam(name = "end-hour") @NotNull LocalTime endHour,
            @RequestParam(name = "garage-id") @NotNull Long garageId,
            @RequestHeader("api-key") String apiKey
    ) throws MyEntityNotFoundException, WrongInputDataException {
        if (apiKey.equals(adminConfig.getAdminApiKey())) {
            bookingDbService.saveBooking(date, startHour, endHour, garageId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized. Wrong api-key.");
        }
    }
}
