package com.backend.controller;

import com.backend.domain.dto.CarDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.facade.CarFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/cars")
@RequiredArgsConstructor
@Validated
public class CarController {
    private final CarFacade carFacade;
    @GetMapping
    public ResponseEntity<List<CarDto>> getCarsForGivenUsername(@RequestParam(name = "username") @NotBlank String username) throws MyEntityNotFoundException {
        return ResponseEntity.ok(carFacade.getCarsForGivenUsername(username));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCar(
            @Valid @RequestBody CarDto carDto,
            @RequestParam(name = "username") @NotBlank String username
    ) throws MyEntityNotFoundException {
        carFacade.createCar(carDto, username);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCar(@Valid @RequestBody CarDto carDto) throws MyEntityNotFoundException {
        carFacade.updateCar(carDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable @Min(1) Long carId) throws MyEntityNotFoundException {
        carFacade.deleteCar(carId);
        return ResponseEntity.ok().build();
    }
}
