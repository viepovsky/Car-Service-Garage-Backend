package com.viepovsky.booking;

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

    public List<BookingDto> getBookingsByDateAndGarageId(LocalDate date, Long garageId) {
        List<Booking> bookingList = bookingService.getBookingsByDateAndGarageId(date, garageId);
        return mapper.mapToBookingDtoList(bookingList);
    }

    public List<BookingDto> getBookingsByUsername(String username) {
        List<Booking> bookingList = bookingService.getAllBookingsByUsername(username);
        return mapper.mapToBookingDtoList(bookingList);
    }

    public List<LocalTime> getAvailableBookingTimes(LocalDate date, int repairDuration, Long garageId, Long carServiceId) {
        LOGGER.info("Get available booking times endpoint used with date:{}, repair duration:{}, garage id:{}, car service id:{}", date, repairDuration, garageId, carServiceId);
        if (carServiceId != 0L) {
            return bookingService.getAvailableBookingTimesByDayAndRepairDuration(date, carServiceId);
        } else {
            return bookingService.getAvailableBookingTimesByDayAndRepairDuration(date, repairDuration, garageId);
        }
    }

    public void createBooking(List<Long> selectedCarRepairIdList, LocalDate date, LocalTime startHour, Long garageId, Long carId, int repairDuration) {
        LOGGER.info("Create booking endpoint used for service ids:{}, date:{}, garage id:{}, and car id:{}.", selectedCarRepairIdList, date, garageId, carId);
        bookingService.createBooking(selectedCarRepairIdList, date, startHour, garageId, carId, repairDuration);
    }

    public void createWorkingHoursBooking(LocalDate date, LocalTime startHour, LocalTime endHour, Long garageId) {
        LOGGER.info("Create working hours booking used for date:{}, garageId:{}", date, garageId);
        bookingService.createWorkingHoursBooking(date, startHour, endHour, garageId);
    }

    public void updateBooking(Long bookingId, LocalDate date, LocalTime startHour) {
        LOGGER.info("Update booking endpoint used for booking id:{}", bookingId);
        bookingService.updateBooking(bookingId, date, startHour);
    }
}
