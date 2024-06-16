package com.viepovsky.visit;

import com.viepovsky.security.DataOwnershipValidator;
import com.viepovsky.utility.mapper.VisitMapper;
import com.viepovsky.visit.dto.VisitDto;
import com.viepovsky.visit.dto.VisitFullDetailDto;
import com.viepovsky.visit.model.Visit;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
class VisitFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(VisitFacade.class);
    private final VisitService visitService;
    private final VisitMapper mapper;
    private final DataOwnershipValidator dataOwnershipValidator;

    public List<VisitDto> getVisitsForGarageAndDate(Long garageId, LocalDate date) {
        LOGGER.info("Retrieving info about visits for garage id:{}, and date:{}", garageId, date);
        List<Visit> visits = visitService.getVisitsForGarageAndDate(garageId, date);
        return mapper.toVisitDto(visits);
    }

    public List<VisitFullDetailDto> getAllVisits(String username) {
        dataOwnershipValidator.belongsToAuthenticatedUser(username);
        LOGGER.info("Retrieving info about visits for username:{}", username);
        List<Visit> visits = visitService.getAllVisits(username);
        return mapper.toFullDetailVisitDto(visits);
    }

    public List<LocalTime> getAvailableBookingTimes(LocalDate date,
                                                    int repairDuration,
                                                    Long garageId,
                                                    Long carServiceId) {
        LOGGER.info("Get available booking times endpoint used with day:{}, repair duration:{}, garage id:{}, car service id:{}", date, repairDuration, garageId, carServiceId);
        if (carServiceId != 0L) {
            return visitService.getAvailableBookingTimesByDayAndRepairDuration(date, carServiceId);
        } else {
            return visitService.getAvailableBookingTimesByDayAndRepairDuration(date, repairDuration, garageId);
        }
    }

    public void createBooking(List<Long> selectedCarRepairIdList,
                              LocalDate date,
                              LocalTime startHour,
                              Long garageId,
                              Long carId,
                              int repairDuration) {
        LOGGER.info("Create booking endpoint used for service ids:{}, day:{}, garage id:{}, and car id:{}.", selectedCarRepairIdList, date, garageId, carId);
        visitService.createBooking(selectedCarRepairIdList, date, startHour, garageId, carId, repairDuration);
    }

    public void createWorkingHoursBooking(LocalDate date,
                                          LocalTime startHour,
                                          LocalTime endHour,
                                          Long garageId) {
        LOGGER.info("Create working hours booking used for day:{}, garageId:{}", date, garageId);
        visitService.createWorkingHoursBooking(date, startHour, endHour, garageId);
    }

    public void updateBooking(Long bookingId,
                              LocalDate date,
                              LocalTime startHour) {
        LOGGER.info("Update booking endpoint used for booking id:{}", bookingId);
        visitService.updateBooking(bookingId, date, startHour);
    }
}
