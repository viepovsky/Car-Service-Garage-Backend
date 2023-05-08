package com.viepovsky.booking;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.exceptions.WrongInputDataException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/bookings")
@RequiredArgsConstructor
@Validated
class BookingController {
    private final BookingFacade facade;

    @GetMapping(path = "/work-time")
    ResponseEntity<List<BookingDto>> getBookings(
            @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(name = "garage-id") @Min(1) Long garageId
    ) {
        return ResponseEntity.ok(facade.getBookingsForGivenDateAndGarageId(date, garageId));
    }

    @GetMapping
    ResponseEntity<List<BookingDto>> getBookings(@RequestParam(name = "name") @NotBlank String username) throws MyEntityNotFoundException {
        String usernameFromToken = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!usernameFromToken.equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(facade.getBookingsForGivenUsername(username));
    }

    @GetMapping(path = "/available-times")
    ResponseEntity<List<LocalTime>> getAvailableBookingTimes(
            @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(name = "repair-duration", required = false, defaultValue = "0") int repairDuration,
            @RequestParam(name = "garage-id", required = false, defaultValue = "0") Long garageId,
            @RequestParam(name = "car-service-id", required = false, defaultValue = "0") Long carServiceId
    ) throws MyEntityNotFoundException {
        return ResponseEntity.ok(facade.getAvailableBookingTimes(date, repairDuration, garageId, carServiceId));
    }

    @PostMapping
    ResponseEntity<Void> createBooking(
            @RequestParam(name = "service-id") @NotEmpty List<Long> selectedServiceIdList,
            @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(name = "start-hour") @NotNull @DateTimeFormat(pattern = "HH:mm") LocalTime startHour,
            @RequestParam(name = "garage-id") @Min(1) Long garageId,
            @RequestParam(name = "car-id") @Min(1) Long carId,
            @RequestParam(name = "repair-duration") @NotNull int repairDuration
    ) throws MyEntityNotFoundException, WrongInputDataException {
        facade.createBooking(selectedServiceIdList, date, startHour, garageId, carId, repairDuration);
        return ResponseEntity.created(URI.create("")).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/admin")
    ResponseEntity<String> createWorkingHoursBooking(
            @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(name = "start-hour") @NotNull @DateTimeFormat(pattern = "HH:mm") LocalTime startHour,
            @RequestParam(name = "end-hour") @NotNull @DateTimeFormat(pattern = "HH:mm") LocalTime endHour,
            @RequestParam(name = "garage-id") @Min(1) Long garageId
    ) throws MyEntityNotFoundException, WrongInputDataException {
        facade.createWorkingHoursBooking(date, startHour, endHour, garageId);
        return ResponseEntity.created(URI.create("/v1/bookings/work-time?date=" + date.toString() + "&garage-id=" + garageId)).build();

    }

    @PutMapping(path = "/{bookingId}")
    ResponseEntity<Void> updateBooking(
            @PathVariable @Min(1) Long bookingId,
            @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(name = "start-hour") @NotNull @DateTimeFormat(pattern = "HH:mm") LocalTime startHour
    ) throws MyEntityNotFoundException {
        facade.updateBooking(bookingId, date, startHour);
        return ResponseEntity.noContent().build();
    }
}
