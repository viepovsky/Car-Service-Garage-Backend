package com.backend.controller;

import com.backend.domain.dto.CarServiceDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.facade.CarServiceFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/car-services")
@RequiredArgsConstructor
@Validated
public class CarServiceController {
    private final CarServiceFacade carServiceFacade;

    @GetMapping
    public ResponseEntity<List<CarServiceDto>> getCarServices(@RequestParam @NotBlank String username) throws MyEntityNotFoundException {
        return ResponseEntity.ok(carServiceFacade.getCarServices(username));
    }

    @PostMapping
    public ResponseEntity<Void> addCarService(
            @RequestParam(name = "service-id") @NotEmpty List<Long> selectedServicesIdList,
            @RequestParam(name = "car-id") @Min(1) Long carId
    ) throws MyEntityNotFoundException {
        carServiceFacade.addCarService(selectedServicesIdList, carId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{carServiceId}")
    public ResponseEntity<Void> deleteCarService(@PathVariable @Min(1) Long carServiceId) throws MyEntityNotFoundException {
        carServiceFacade.deleteCarService(carServiceId);
        return ResponseEntity.ok().build();
    }
}
