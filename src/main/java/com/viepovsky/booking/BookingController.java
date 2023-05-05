package com.viepovsky.booking;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.exceptions.WrongInputDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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
class BookingController {
    private final BookingFacade bookingFacade;

    @GetMapping
    public ResponseEntity<List<BookingDto>> getBookings(@RequestParam(name = "name") @NotBlank String username) throws MyEntityNotFoundException {
        return ResponseEntity.ok(bookingFacade.getBookingsForGivenUsername(username));
    }

    @GetMapping(path = "/available-times")
    public ResponseEntity<List<LocalTime>> getAvailableBookingTimes(
            @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(name = "repair-duration", required = false, defaultValue = "0") int repairDuration,
            @RequestParam(name = "garage-id", required = false, defaultValue = "0") Long garageId,
            @RequestParam(name = "car-service-id", required = false, defaultValue = "0") Long carServiceId
    ) throws MyEntityNotFoundException {
        return ResponseEntity.ok(bookingFacade.getAvailableBookingTimes(date, repairDuration, garageId, carServiceId));
    }

    @PostMapping
    public ResponseEntity<Void> createBooking(
            @RequestParam(name = "service-id") @NotEmpty List<Long> selectedServiceIdList,
            @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(name = "start-hour") @NotNull @DateTimeFormat(pattern = "HH:mm") LocalTime startHour,
            @RequestParam(name = "garage-id") @Min(1) Long garageId,
            @RequestParam(name = "car-id") @Min(1) Long carId,
            @RequestParam(name = "repair-duration") @NotNull int repairDuration
    ) throws MyEntityNotFoundException, WrongInputDataException {
        bookingFacade.createBooking(selectedServiceIdList, date, startHour, garageId, carId, repairDuration);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/admin")
    public ResponseEntity<String> createAvailableBooking(
            @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(name = "start-hour") @NotNull @DateTimeFormat(pattern = "HH:mm") LocalTime startHour,
            @RequestParam(name = "end-hour") @NotNull @DateTimeFormat(pattern = "HH:mm") LocalTime endHour,
            @RequestParam(name = "garage-id") @Min(1) Long garageId,
            @RequestHeader("api-key") @NotBlank String apiKey
    ) throws MyEntityNotFoundException, WrongInputDataException {
        return bookingFacade.createAvailableBookingDays(date, startHour, endHour, garageId, apiKey);
    }

    @PutMapping(path = "/{bookingId}")
    public ResponseEntity<Void> updateBooking(
            @PathVariable @Min(1) Long bookingId,
            @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(name = "start-hour") @NotNull @DateTimeFormat(pattern = "HH:mm") LocalTime startHour
    ) throws MyEntityNotFoundException {
        bookingFacade.updateBooking(bookingId, date, startHour);
        return ResponseEntity.ok().build();
    }
}
