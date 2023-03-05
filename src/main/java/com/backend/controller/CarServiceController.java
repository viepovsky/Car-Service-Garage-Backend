package com.backend.controller;

import com.backend.domain.CarService;
import com.backend.domain.dto.CarServiceDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.CarServiceMapper;
import com.backend.service.CarServiceDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/car-services")
@RequiredArgsConstructor
public class CarServiceController {
    private final CarServiceDbService carServiceDbService;
    private final CarServiceMapper carServiceMapper;

    @GetMapping(path = "/{carServiceId}")
    public ResponseEntity<CarServiceDto> getCarService(@PathVariable Long carServiceId) throws MyEntityNotFoundException {
        CarService carService = carServiceDbService.getCarService(carServiceId);
        return ResponseEntity.ok(carServiceMapper.mapToCarServiceDto(carService));
    }

    @PostMapping
    public ResponseEntity<Void> addCarService(@RequestParam List<Long> selectedServices, @RequestParam Long carId, @RequestParam Long bookingId) throws MyEntityNotFoundException {
        carServiceDbService.saveCarService(selectedServices, carId, bookingId);
        return ResponseEntity.ok().build();
    }

}
