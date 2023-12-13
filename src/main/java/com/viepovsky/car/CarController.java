package com.viepovsky.car;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/cars")
@RequiredArgsConstructor
@Validated
class CarController {
    private final CarFacade carFacade;

    @GetMapping
    ResponseEntity<List<CarDto>> getCarsForGivenUsername(@RequestParam(name = "username") @NotBlank String username) {
        String usernameFromToken = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!usernameFromToken.equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(carFacade.getCarsForGivenUsername(username));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createCar(
            @Valid @RequestBody CarDto carDto,
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
    ResponseEntity<Void> updateCar(@Valid @RequestBody CarDto carDto) {
        carFacade.updateCar(carDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{carId}")
    ResponseEntity<Void> deleteCar(@PathVariable @Min(1) Long carId) {
        carFacade.deleteCar(carId);
        return ResponseEntity.ok().build();
    }
}
