package com.viepovsky.booking;

import com.viepovsky.exceptions.WrongInputDataException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
class BookingFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingFacade.class);
    private final BookingService bookingService;
    private final BookingMapper mapper;

    public List<BookingDto> getBookingsForGivenDateAndGarageId(LocalDate date, Long garageId) {
        List<Booking> bookingList = bookingService.getBookingsForGivenDateAndGarageId(date, garageId);
        return mapper.mapToBookingDtoList(bookingList);
    }

    public List<BookingDto> getBookingsForGivenUsername(String username) {
        List<Booking> bookingList = bookingService.getAllBookingsForGivenUser(username);
        return mapper.mapToBookingDtoList(bookingList);
    }

    public List<LocalTime> getAvailableBookingTimes(LocalDate date, int repairDuration, Long garageId, Long carServiceId) {
        LOGGER.info("GET Endpoint getAvailableBookingTimes used.");
        if (carServiceId != 0L) {
            return bookingService.getAvailableBookingTimesForSelectedDayAndRepairDuration(date, carServiceId);
        } else {
            return bookingService.getAvailableBookingTimesForSelectedDayAndRepairDuration(date, repairDuration, garageId);
        }
    }

    public void createBooking(List<Long> selectedServiceIdList, LocalDate date, LocalTime startHour, Long garageId, Long carId, int repairDuration) throws WrongInputDataException {
        LOGGER.info("POST Endpoint createBooking used.");
        bookingService.createBooking(selectedServiceIdList, date, startHour, garageId, carId, repairDuration);
    }

    public void createWorkingHoursBooking(LocalDate date, LocalTime startHour, LocalTime endHour, Long garageId) throws WrongInputDataException {
        LOGGER.info("POST Endpoint createAvailableBooking used.");
        bookingService.saveBooking(date, startHour, endHour, garageId);
    }

    public void updateBooking(Long bookingId, LocalDate date, LocalTime startHour) {
        LOGGER.info("PUT Endpoint getAvailableBookingTimes used.");
        bookingService.updateBooking(bookingId, date, startHour);
    }
}
