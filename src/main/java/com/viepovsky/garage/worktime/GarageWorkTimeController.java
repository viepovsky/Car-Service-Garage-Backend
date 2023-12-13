package com.viepovsky.garage.worktime;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/garage-work-time")
@RequiredArgsConstructor
@Validated
class GarageWorkTimeController {
    private final GarageWorkTimeFacade facade;

    @GetMapping(path = "/{garageId}")
    ResponseEntity<List<GarageWorkTimeDto>> getGarageWorkTimes(@PathVariable @Min(1) Long garageId) {
        return ResponseEntity.ok(facade.getGarageWorkTimes(garageId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{garageId}")
    ResponseEntity<String> createGarageWorkTime(
            @Valid @RequestBody GarageWorkTimeDto garageWorkTimeDto,
            @PathVariable @Min(1) Long garageId
    ) {
        facade.createGarageWorkTime(garageWorkTimeDto, garageId);
        return ResponseEntity.created(URI.create("/v1/garage-work-time/" + garageId)).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{garageWorkTimeId}")
    ResponseEntity<String> deleteGarageWorkTime(@PathVariable @Min(1) Long garageWorkTimeId) {
        facade.deleteGarageWorkTime(garageWorkTimeId);
        return ResponseEntity.ok().build();
    }
}
