package com.backend.api.car.controller;

import com.backend.api.car.service.CarApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/car-api")
@RequiredArgsConstructor
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
    public ResponseEntity<List<String>> getCarModels(@RequestParam Integer year, @RequestParam String make, @RequestParam String type) {
        return ResponseEntity.ok(carApiService.getCarModels(year, make, type));
    }
}
