package com.backend.controller;

import com.backend.domain.Car;
import com.backend.domain.dto.CarDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.CarMapper;
import com.backend.service.CarDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarDbService carDbService;
    private final CarMapper carMapper;

    @GetMapping(path = "/{carId}")
    public ResponseEntity<CarDto> getCar(@PathVariable Long carId) throws MyEntityNotFoundException {
        Car car = carDbService.getCar(carId);
        return ResponseEntity.ok(carMapper.mapToCarDto(car));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCar(@RequestBody CarDto carDto) throws MyEntityNotFoundException {
        Car car = carMapper.mapToCar(carDto);
        carDbService.saveCar(car, carDto.getCustomerId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long carId) throws MyEntityNotFoundException {
        carDbService.deleteCar(carId);
        return ResponseEntity.ok().build();
    }
}
