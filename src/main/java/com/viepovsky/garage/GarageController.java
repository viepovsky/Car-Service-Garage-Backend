package com.viepovsky.garage;

import com.viepovsky.garage.dto.GarageCreateRequest;
import com.viepovsky.garage.dto.GarageDto;
import com.viepovsky.garage.dto.ScheduleCreateRequest;
import com.viepovsky.garage.dto.ScheduleDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/garages")
@RequiredArgsConstructor
@Validated
class GarageController {

    private final GarageFacade garageFacade;

    @GetMapping
    ResponseEntity<List<GarageDto>> getAllGarages() {
        return ResponseEntity.ok(garageFacade.getAllGarages());
    }

    @GetMapping(path = "/{garageId}")
    ResponseEntity<GarageDto> getGarage(@PathVariable @Min(1) Long garageId) {
        return ResponseEntity.ok(garageFacade.getGarage(garageId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GarageDto> createGarage(@Valid @RequestBody GarageCreateRequest request) {
        var createdGarage = garageFacade.createGarage(request);
        return ResponseEntity.created(URI.create("/v1/garages/" + createdGarage.id()))
                .body(createdGarage);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{garageId}")
    ResponseEntity<Void> deleteGarage(@PathVariable @Min(1) Long garageId) {
        garageFacade.deleteGarage(garageId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/schedule/{garageId}")
    ResponseEntity<List<ScheduleDto>> getGarageSchedules(@PathVariable @Min(1) Long garageId) {
        return ResponseEntity.ok(garageFacade.getSchedulesFor(garageId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/schedule/{garageId}")
    ResponseEntity<ScheduleDto> createSchedule(
            @Valid @RequestBody ScheduleCreateRequest request,
            @PathVariable @Min(1) Long garageId) {
        var createdSchedule = garageFacade.createSchedule(request, garageId);
        return ResponseEntity.created(URI.create("/v1/garages/schedule/" + garageId)).body(createdSchedule);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/schedule/{scheduleId}")
    ResponseEntity<Void> deleteGarageSchedule(@PathVariable @Min(1) Long scheduleId) {
        garageFacade.deleteSchedule(scheduleId);
        return ResponseEntity.ok().build();
    }
}
