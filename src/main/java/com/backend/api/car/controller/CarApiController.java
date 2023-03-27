package com.backend.api.car.controller;

import com.backend.api.car.service.CarApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/car-api")
@RequiredArgsConstructor
@Validated
public class CarApiController {
    private final CarApiService carApiService;

    @GetMapping(path = "/makes")
    public ResponseEntity<List<String>> getCarMakes() {
        return ResponseEntity.ok(carApiService.getCarMakes());
    }
    @GetMapping(path = "/types")
    public ResponseEntity<List<String>> getCarTypes() {
        return ResponseEntity.ok(carApiService.getCarTypes());
    }
    @GetMapping(path = "/years")
    public ResponseEntity<List<Integer>> getCarYears() {
        return ResponseEntity.ok(carApiService.getCarYears());
    }
    @GetMapping
    public ResponseEntity<List<String>> getCarModels(
            @RequestParam @Min(1950) Integer year,
            @RequestParam @NotBlank String make,
            @RequestParam @NotBlank String type
    ) throws InterruptedException {
        return ResponseEntity.ok(carApiService.getCarModels(year, make, type));
    }
}
