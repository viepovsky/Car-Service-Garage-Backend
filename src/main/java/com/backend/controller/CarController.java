package com.backend.controller;

import com.backend.domain.Car;
import com.backend.domain.dto.CarDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.CarMapper;
import com.backend.service.CarDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/cars")
@RequiredArgsConstructor
@Validated
public class CarController {

    private final CarDbService carDbService;
    private final CarMapper carMapper;

    @GetMapping(path = "/{carId}")
    public ResponseEntity<CarDto> getCar(@PathVariable Long carId) throws MyEntityNotFoundException {
        Car car = carDbService.getCar(carId);
        return ResponseEntity.ok(carMapper.mapToCarDto(car));
    }
    @GetMapping
    public ResponseEntity<List<CarDto>> getCarsForGivenUsername(@RequestParam @NotBlank String username) throws MyEntityNotFoundException {
        List<Car> carList = carDbService.getAllCarsForGivenUsername(username);
        return ResponseEntity.ok(carMapper.mapToCarDtoList(carList));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCar(
            @Valid @RequestBody CarDto carDto,
            @RequestParam(name = "username") @NotBlank String username
    ) throws MyEntityNotFoundException {
        Car car = carMapper.mapToCar(carDto);
        carDbService.saveCar(car, username);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCar(@Valid @RequestBody CarDto carDto) throws MyEntityNotFoundException {
        carDbService.updateCar(carMapper.mapToCar(carDto));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long carId) throws MyEntityNotFoundException {
        carDbService.deleteCar(carId);
        return ResponseEntity.ok().build();
    }
}
