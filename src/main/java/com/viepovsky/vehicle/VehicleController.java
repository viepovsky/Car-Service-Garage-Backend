package com.viepovsky.vehicle;

import com.viepovsky.vehicle.dto.VehicleDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/cars")
@RequiredArgsConstructor
@Validated
class VehicleController {

    private final VehicleFacade carFacade;

    @GetMapping
    ResponseEntity<List<VehicleDto>> getCarsForGivenUsername(@RequestParam(name = "username") @NotBlank String username) {
        String usernameFromToken = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!usernameFromToken.equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(carFacade.getCarsForGivenUsername(username));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createCar(
            @Valid @RequestBody VehicleDto carDto,
            @RequestParam(name = "username") @NotBlank String username
    ) {
        String usernameFromToken = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!usernameFromToken.equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        carFacade.createCar(carDto, username);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updateCar(@Valid @RequestBody VehicleDto carDto) {
        carFacade.updateCar(carDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{carId}")
    ResponseEntity<Void> deleteCar(@PathVariable @Min(1) Long carId) {
        carFacade.deleteCar(carId);
        return ResponseEntity.ok().build();
    }
}
