package com.viepovsky.visit;

import com.viepovsky.garage.GarageService;
import com.viepovsky.garage.model.Garage;
import com.viepovsky.garage.model.Schedule;
import com.viepovsky.offer.CatalogOfferService;
import com.viepovsky.offer.SelectedOfferService;
import com.viepovsky.offer.model.SelectedOffer;
import com.viepovsky.user.UserService;
import com.viepovsky.user.model.AppUser;
import com.viepovsky.utility.exceptions.MyEntityNotFoundException;
import com.viepovsky.utility.exceptions.WrongInputDataException;
import com.viepovsky.utility.mappers.SelectedOfferMapper;
import com.viepovsky.vehicle.VehicleService;
import com.viepovsky.vehicle.model.Vehicle;
import com.viepovsky.visit.model.Visit;
import com.viepovsky.visit.model.VisitStatus;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VisitService.class);
    private final VisitRepository visitRepository;
    private final GarageService garageService;
    private final CatalogOfferService catalogOfferService;
    private final SelectedOfferService selectedOfferService;
    private final VehicleService vehicleService;
    private final UserService userService;
    private final SelectedOfferMapper selectedOfferMapper;

    public Visit getVisit(Long visitId) {
        return visitRepository
                .findById(visitId)
                .orElseThrow(() -> new MyEntityNotFoundException("Visit" + visitId));
    }

    public List<Visit> getAllVisits(String username) {
        Long userId = userService.getUser(username).getId();
        return visitRepository.getAllVisits(userId);
    }

    public List<Visit> getVisitsForGarageAndDate(Long garageId, LocalDate date) {
        return visitRepository.getAllVisitsForGarageAndDate(garageId, date);
    }

    private Visit getById(Long id) {
        return visitRepository.findById(id)
                              .orElseThrow(() -> new MyEntityNotFoundException("Visit" + id));
    }

    public List<LocalTime> getAvailableVisitTimes(LocalDate date, Long selectedOfferId) {
        SelectedOffer selectedOffer = selectedOfferService.getById(selectedOfferId);
        Visit reservedVisit = getById(selectedOffer.getVisit().getId());
        Long garageId = reservedVisit.getGarage().getId();
        Optional<Schedule> optionalSchedule = garageService.getScheduleFor(date, garageId);
        if (optionalSchedule.isEmpty()) {
            LOGGER.info("Garage is closed on date:{}", date);
            return new ArrayList<>();
        }
        int sumRepairDuration =
                reservedVisit.getSelectedOffers().stream()
                        .mapToInt(SelectedOffer::getProbableRepairTime)
                        .sum();

        LOGGER.info(
                "Given parameters to get available times, date: {}, total repair time: {}, garage id: {}",
                date,
                sumRepairDuration,
                garageId);
        List<Visit> visitsOnDate = visitRepository.getAllVisitsForGarageAndDate(garageId, date);
        visitsOnDate.remove(reservedVisit);

        List<LocalTime> availableVisitTimes =
                checkAndReturnAvailableVisitTimes(
                        visitsOnDate, date, sumRepairDuration, optionalSchedule.get());
        availableVisitTimes.remove(reservedVisit.getVisitStartTime());
        return availableVisitTimes;
    }

    public List<LocalTime> getAvailableVisitTimes(LocalDate date, int repairDuration, Long garageId) {
        LOGGER.info("Given parameters to get available times, date: {}, total repair time: {}, garage id: {}", date, repairDuration, garageId);
        Optional<Schedule> optionalSchedule = garageService.getScheduleFor(date, garageId);
        if (optionalSchedule.isEmpty()) {
            LOGGER.info("Garage is closed on date:{}", date);
            return new ArrayList<>();
        }
        List<Visit> visitsOnDate = visitRepository.getAllVisitsForGarageAndDate(garageId, date);
        return checkAndReturnAvailableVisitTimes(visitsOnDate, date, repairDuration, optionalSchedule.get());
    }

    private List<LocalTime> checkAndReturnAvailableVisitTimes(
            List<Visit> visitsOnDate, LocalDate date, int repairDuration, Schedule schedule) {
        LocalTime openTime = schedule.getOpenTime();
        LocalTime closeTime = schedule.getCloseTime();
        if (isCloseTimeBeforeNow(closeTime)) {
            return new ArrayList<>();
        }
        List<Visit> visitsNotFinished =
                visitsOnDate.stream()
                        .filter(visit -> visit.getStatus() != VisitStatus.COMPLETED)
                        .toList();
        if (isOpenTimeBeforeNow(date, openTime)) {
            LocalTime currentTime = timeNowRoundUpToNearest10Minutes();
            return getAvailableTimesForVisit(
                    currentTime, closeTime, repairDuration, visitsNotFinished);
        } else {
            return getAvailableTimesForVisit(
                    openTime, closeTime, repairDuration, visitsNotFinished);
        }
    }

    private boolean isCloseTimeBeforeNow(LocalTime closeTime) {
        return closeTime.isBefore(LocalTime.now());
    }

    private boolean isOpenTimeBeforeNow(LocalDate date, LocalTime openTime) {
        return (date.isEqual(LocalDate.now()) && LocalTime.now().isAfter(openTime));
    }

    private LocalTime timeNowRoundUpToNearest10Minutes() {
        LocalTime timeNow = LocalTime.now();
        int minutes = timeNow.getMinute() + timeNow.getHour() * 60;
        minutes = ((minutes + 10) / 10) * 10;
        return LocalTime.of(minutes / 60, minutes % 60);
    }

    private List<LocalTime> getAvailableTimesForVisit(
            LocalTime currentTime,
            LocalTime closeTime,
            int repairDuration,
            List<Visit> visitsNotFinished) {
        List<LocalTime> availableVisitTimes = new ArrayList<>();
        while (!currentTime.plusMinutes(repairDuration).isAfter(closeTime)) {
            boolean isCurrentTimeAvailable = true;
            for (Visit visit : visitsNotFinished) {
                LocalTime startTime = visit.getVisitStartTime();
                LocalTime endTime = visit.getVisitEndTime();
                if (currentTime.plusMinutes(repairDuration).isAfter(startTime) && endTime.isAfter(currentTime)) {
                    isCurrentTimeAvailable = false;
                    break;
                }
            }
            if (isCurrentTimeAvailable) {
                availableVisitTimes.add(currentTime);
            }
            currentTime = currentTime.plusMinutes(10);
        }
        return availableVisitTimes;
    }

    public void updateBooking(Long bookingId, LocalDate date, LocalTime startHour) {
        Visit booking = visitRepository.findById(bookingId)
                                       .orElseThrow(() -> new MyEntityNotFoundException("Booking" + bookingId));
        int repairTime = (int) Duration.between(booking.getVisitStartTime(), booking.getVisitEndTime()).toMinutes();
        booking.setVisitStartDate(date);
        booking.setVisitStartTime(startHour);
        booking.setVisitEndTime(startHour.plusMinutes(repairTime));
        LOGGER.info("Updated booking with values, date: " + date + ", time: " + startHour);
        visitRepository.save(booking);
    }

    public Visit createVisit(
            List<Long> catalogOfferIds,
            LocalDate date,
            LocalTime startHour,
            Long garageId,
            Long vehicleId,
            int repairDuration) {
        Garage garage = garageService.getGarage(garageId);
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);
        AppUser user = vehicle.getUser();
        List<LocalTime> availableVisitTimes =
                getAvailableVisitTimes(date, repairDuration, garageId);
        if (availableVisitTimes.contains(startHour)) {
            List<SelectedOffer> selectedOffers = createSelectedOffers(catalogOfferIds);
            BigDecimal totalPrice =
                    selectedOffers.stream()
                            .map(SelectedOffer::getPrice)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
            Visit visit =
                    new Visit(
                            date,
                            startHour,
                            startHour.plusMinutes(repairDuration),
                            date,
                            VisitStatus.WAITING_FOR_CUSTOMER,
                            garage,
                            user,
                            vehicle,
                            selectedOffers,
                            totalPrice);
            garage.getVisits().add(visit);
            selectedOffers.forEach(selectedOffer -> selectedOffer.setVisit(visit));
            user.getVisits().add(visit);
            vehicle.getVisits().add(visit);
            return visitRepository.save(visit);
        } else {
            throw new WrongInputDataException(
                    "Given time: " + startHour + " is no longer available. Choose another date.");
        }
    }

    private List<SelectedOffer> createSelectedOffers(List<Long> catalogOfferIds) {
        return catalogOfferIds.stream()
                .map(catalogOfferService::getById)
                .map(selectedOfferMapper::toSelectedOffer)
                .toList();
    }

    public void save(Visit booking) {
        visitRepository.save(booking);
    }

    public void delete(Visit booking) {
        visitRepository.delete(booking);
    }
}
