package com.backend.controller;

import com.backend.domain.CarService;
import com.backend.domain.dto.CarServiceDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.CarServiceMapper;
import com.backend.service.CarServiceDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/car-services")
@RequiredArgsConstructor
@Validated
public class CarServiceController {
    private final CarServiceDbService carServiceDbService;
    private final CarServiceMapper carServiceMapper;

    @GetMapping(path = "/{carServiceId}")
    public ResponseEntity<CarServiceDto> getCarService(@PathVariable Long carServiceId) throws MyEntityNotFoundException {
        CarService carService = carServiceDbService.getCarService(carServiceId);
        return ResponseEntity.ok(carServiceMapper.mapToCarServiceDto(carService));
    }

    @PostMapping
    public ResponseEntity<Void> addCarService(
            @RequestParam(name = "service-id") @NotEmpty List<Long> selectedServicesIdList,
            @RequestParam(name = "car-id") @NotNull Long carId
    ) throws MyEntityNotFoundException {
        carServiceDbService.saveCarService(selectedServicesIdList, carId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/car")
    public ResponseEntity<Void> deleteServicesNotAssignedToBooking(@NotNull @RequestParam Long carId) throws MyEntityNotFoundException {
        carServiceDbService.deleteServicesNotAssignedToBooking(carId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{carServiceId}")
    public ResponseEntity<Void> deleteCarService(@PathVariable Long carServiceId) throws MyEntityNotFoundException {
        carServiceDbService.deleteCarService(carServiceId);
        return ResponseEntity.ok().build();
    }
}
