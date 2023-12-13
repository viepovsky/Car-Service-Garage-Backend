package com.viepovsky.clients.car;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/car-api")
@RequiredArgsConstructor
@Validated
class CarApiController {
    private final CarApiService service;

    @GetMapping(path = "/makes")
    ResponseEntity<List<String>> getCarMakes() {
        return ResponseEntity.ok(service.getCarMakes());
    }

    @GetMapping(path = "/types")
    ResponseEntity<List<String>> getCarTypes() {
        return ResponseEntity.ok(service.getCarTypes());
    }

    @GetMapping(path = "/years")
    ResponseEntity<List<Integer>> getCarYears() {
        return ResponseEntity.ok(service.getCarYears());
    }

    @GetMapping
    ResponseEntity<List<String>> getCarModels(
            @RequestParam @Min(1950) Integer year,
            @RequestParam @NotBlank String make,
            @RequestParam @NotBlank String type
    ) throws InterruptedException {
        return ResponseEntity.ok(service.getCarModels(year, make, type));
    }
}
