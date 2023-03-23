package com.backend.facade;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingFacade.class);
    private final BookingDbService bookingDbService;
    private final BookingMapper bookingMapper;
    private final AdminConfig adminConfig;
    public List<BookingDto> getBookingsForGivenUsername(String username) throws MyEntityNotFoundException {
        List<Booking> bookingList = bookingDbService.getAllBookingsForGivenUser(username);
        return bookingMapper.mapToBookingDtoList(bookingList);
    }
    public List<LocalTime> getAvailableBookingTimes(LocalDate date, int repairDuration, Long garageId, Long carServiceId) throws MyEntityNotFoundException {
        LOGGER.info("GET Endpoint getAvailableBookingTimes used.");
        if (carServiceId != 0L) {
            return bookingDbService.getAvailableBookingTimesForSelectedDayAndRepairDuration(date, carServiceId);
        } else {
            return bookingDbService.getAvailableBookingTimesForSelectedDayAndRepairDuration(date, repairDuration, garageId);
        }
    }

    public void createBooking(List<Long> selectedServiceIdList, LocalDate date, LocalTime startHour, Long garageId, Long carId, int repairDuration) throws MyEntityNotFoundException, WrongInputDataException {
        LOGGER.info("POST Endpoint createBooking used.");
        bookingDbService.createBooking(selectedServiceIdList, date, startHour, garageId, carId, repairDuration);
    }

    public ResponseEntity<String> createAvailableBookingDays(LocalDate date, LocalTime startHour, LocalTime endHour, Long garageId, String apiKey) throws MyEntityNotFoundException, WrongInputDataException {
        LOGGER.info("POST Endpoint createAvailableBooking used.");
        if (apiKey.equals(adminConfig.getAdminApiKey())) {
            bookingDbService.saveBooking(date, startHour, endHour, garageId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized. Wrong api-key.");
        }
    }

    public void updateBooking(Long bookingId, LocalDate date, LocalTime startHour) throws MyEntityNotFoundException {
        LOGGER.info("PUT Endpoint getAvailableBookingTimes used.");
        bookingDbService.updateBooking(bookingId, date, startHour);
    }
}
