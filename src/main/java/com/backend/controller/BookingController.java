package com.backend.controller;

import com.backend.config.AdminConfig;
import com.backend.domain.Booking;
import com.backend.domain.dto.BookingDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.exceptions.WrongInputDataException;
import com.backend.mapper.BookingMapper;
import com.backend.service.BookingDbService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);
    private final BookingDbService bookingDbService;
    private final BookingMapper bookingMapper;
    private final AdminConfig adminConfig;

    @GetMapping
    public ResponseEntity<List<BookingDto>> getBookings(@RequestParam(name = "userId") @NotNull Long userId) {
        List<Booking> bookingList = bookingDbService.getAllBookingsForGivenUser(userId);
        return ResponseEntity.ok(bookingMapper.mapToBookingDtoList(bookingList));
    }

//    @GetMapping(path = "/available-times")
//    public ResponseEntity<List<LocalTime>> getAvailableBookingTimes(
//            @RequestParam(name = "date") @NotNull LocalDate date,
//            @RequestParam(name = "service-id") @NotEmpty List<Long> carServiceList,
//            @RequestParam(name = "garage-id") @NotNull Long garageId
//    ) throws MyEntityNotFoundException {
//        return ResponseEntity.ok(bookingDbService.getAvailableBookingTimesForSelectedDayAndCarServices(date, carServiceList, garageId));
//    }

    @GetMapping(path = "/available-times")
    public ResponseEntity<List<LocalTime>> getAvailableBookingTimes(
            @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(name = "repair-duration") @NotNull int repairDuration,
            @RequestParam(name = "garage-id") @NotNull Long garageId
    ) throws MyEntityNotFoundException {
        LOGGER.info("Endpoint GET getAvailableBookingTimes used.");
        return ResponseEntity.ok(bookingDbService.getAvailableBookingTimesForSelectedDayAndRepairDuration(date, repairDuration, garageId));
    }

    @PostMapping
    public ResponseEntity<Void> createBooking(
            @RequestParam(name = "service-id") @NotEmpty List<Long> selectedServiceIdList,
            @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(name = "start-hour") @NotNull LocalTime startHour,
            @RequestParam(name = "garage-id") @NotNull Long garageId,
            @RequestParam(name = "car-id") @NotNull Long carId,
            @RequestParam(name = "repair-duration") @NotNull int repairDuration
    ) throws MyEntityNotFoundException, WrongInputDataException {
        bookingDbService.createBooking(selectedServiceIdList, date, startHour, garageId, carId, repairDuration);
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
