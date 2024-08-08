package com.viepovsky.visit;

import com.viepovsky.visit.dto.VisitDto;
import com.viepovsky.visit.dto.VisitFullDetailDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/visits")
@RequiredArgsConstructor
@Validated
class VisitController {
    private final VisitFacade visitFacade;

    @GetMapping(path = "/{visitId}")
    ResponseEntity<VisitFullDetailDto> getVisit(@PathVariable @Min(1) Long visitId) {
        return ResponseEntity.ok(visitFacade.getVisit(visitId));
    }

    @GetMapping(path = "/garage-date")
    ResponseEntity<List<VisitDto>> getVisitsForGarageAndDate(
            @RequestParam(name = "garage-id") @Min(1) Long garageId,
            @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd")
                    LocalDate date) {
        return ResponseEntity.ok(visitFacade.getVisitsForGarageAndDate(garageId, date));
    }

    @GetMapping
    ResponseEntity<List<VisitFullDetailDto>> getVisits(
            @RequestParam(name = "name") @NotBlank String username) {
        return ResponseEntity.ok(visitFacade.getAllVisits(username));
    }

    @GetMapping(path = "/new-offer-available-times")
    ResponseEntity<List<LocalTime>> getAvailableVisitTimes(
            @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd")
                    LocalDate date,
            @RequestParam(name = "repair-duration") int repairDuration,
            @RequestParam(name = "garage-id") Long garageId) {
        return ResponseEntity.ok(
                visitFacade.getAvailableVisitTimes(date, repairDuration, garageId));
    }

    @GetMapping(path = "/existing-offer-available-times")
    ResponseEntity<List<LocalTime>> getAvailableVisitTimes(
            @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd")
                    LocalDate date,
            @RequestParam(name = "selectedOfferId") Long selectedOfferId) {
        return ResponseEntity.ok(visitFacade.getAvailableVisitTimes(date, selectedOfferId));
    }

    @PostMapping
    ResponseEntity<VisitDto> createVisit(
            @RequestParam(name = "catalog-offer-id") @NotEmpty List<Long> catalogOfferIds,
            @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd")
                    LocalDate date,
            @RequestParam(name = "start-hour") @NotNull @DateTimeFormat(pattern = "HH:mm")
                    LocalTime startHour,
            @RequestParam(name = "garage-id") @Min(1) Long garageId,
            @RequestParam(name = "vehicle-id") @Min(1) Long vehicleId,
            @RequestParam(name = "repair-duration") @NotNull int repairDuration) {
        VisitDto response =
                visitFacade.createVisit(
                        catalogOfferIds, date, startHour, garageId, vehicleId, repairDuration);
        return ResponseEntity.created(URI.create("/v1/visits/" + response.id()))
                .body(response);
    }

    @PutMapping(path = "/{visitId}")
    ResponseEntity<Void> updateVisit(
            @PathVariable @Min(1) Long visitId,
            @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd")
                    LocalDate date,
            @RequestParam(name = "start-hour") @NotNull @DateTimeFormat(pattern = "HH:mm")
                    LocalTime startHour) {
        visitFacade.updateVisit(visitId, date, startHour);
        return ResponseEntity.noContent().build();
    }
}
