package com.viepovsky.garage.availablerepair;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
@RequestMapping("/v1/available-car-service")
@RequiredArgsConstructor
@Validated
class AvailableCarRepairController {
    private final AvailableCarRepairFacade facade;

    @GetMapping(path = "/{garageId}")
    ResponseEntity<List<AvailableCarRepairDto>> getAvailableCarServices(@PathVariable @Min(1) Long garageId) {
        return ResponseEntity.ok(facade.getAvailableCarServices(garageId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> createAvailableCarService(
            @Valid @RequestBody AvailableCarRepairDto availableCarRepairDto,
            @RequestParam(name = "garage-id") @NotNull @Min(1) Long garageId
    ) throws MyEntityNotFoundException {
        facade.createAvailableCarService(availableCarRepairDto, garageId);
        return ResponseEntity.created(URI.create("/v1/available-car-service/" + garageId)).build();
    }

    @DeleteMapping(path = "/{availableCarServiceId}")
    ResponseEntity<String> deleteAvailableCarService(@PathVariable @Min(1) Long availableCarServiceId) throws MyEntityNotFoundException {
        facade.deleteAvailableCarService(availableCarServiceId);
        return ResponseEntity.ok().build();
    }
}
