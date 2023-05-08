package com.viepovsky.garage;

import com.viepovsky.exceptions.MyEntityNotFoundException;
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
@RequestMapping("/v1/garages")
@RequiredArgsConstructor
@Validated
class GarageController {
    private final GarageFacade facade;

    @GetMapping
    ResponseEntity<List<GarageDto>> getGarages() {
        return ResponseEntity.ok(facade.getAllGarages());
    }

    @GetMapping(path = "/{garageId}")
    ResponseEntity<GarageDto> getGarage(@PathVariable @Min(1) Long garageId) {
        return ResponseEntity.ok(facade.getGarage(garageId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> createGarage(@Valid @RequestBody GarageDto garageDto) {
        var createdGarage = facade.createGarage(garageDto);
        return ResponseEntity.created(URI.create("/v1/garages/" + createdGarage.getId())).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{garageId}")
    ResponseEntity<String> deleteGarage(@PathVariable @Min(1) Long garageId) throws MyEntityNotFoundException {
        facade.deleteGarage(garageId);
        return ResponseEntity.ok().build();
    }
}
