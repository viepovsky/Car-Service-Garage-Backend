package com.viepovsky.visit;

import com.viepovsky.security.DataOwnershipValidator;
import com.viepovsky.utility.mappers.VisitMapper;
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

    public VisitFullDetailDto getVisit(Long visitId) {
        LOGGER.info("Retrieving visit of id:{}", visitId);
        Visit visit = visitService.getVisit(visitId);
        dataOwnershipValidator.belongsToAuthenticatedUser(visit.getUser().getUsername());
        return mapper.toFullDetailVisitDto(visit);
    }

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

    public List<LocalTime> getAvailableVisitTimes(
            LocalDate date, int repairDuration, Long garageId) {
        LOGGER.info(
                "Get available booking times endpoint used with date:{}, repair duration:{}, garage id:{}",
                date,
                repairDuration,
                garageId);
        return visitService.getAvailableVisitTimes(
                date, repairDuration, garageId);
    }

    public List<LocalTime> getAvailableVisitTimes(LocalDate date, Long selectedOfferId) {
        LOGGER.info(
                "Get available booking times endpoint used with date:{}, selected offer id:{}",
                date,
                selectedOfferId);
        return visitService.getAvailableVisitTimes(date, selectedOfferId);
    }

    public VisitDto createVisit(List<Long> catalogOfferIds,
                            LocalDate date,
                            LocalTime startHour,
                            Long garageId,
                            Long vehicleId,
                            int repairDuration) {
        LOGGER.info("Create visit endpoint used for service ids:{}, date:{}, garage id:{}, and car id:{}.", catalogOfferIds, date, garageId, vehicleId);
        Visit createdVisit = visitService.createVisit(catalogOfferIds, date, startHour, garageId, vehicleId, repairDuration);
        return mapper.toVisitDto(createdVisit);
    }

    public void updateVisit(Long visitId, LocalDate date, LocalTime startHour) {
        LOGGER.info("Update visit endpoint used for visit id:{}", visitId);
        Visit visit = visitService.getVisit(visitId);
        dataOwnershipValidator.belongsToAuthenticatedUser(visit.getUser().getUsername());
        visitService.updateVisit(visit, date, startHour);
    }
}
