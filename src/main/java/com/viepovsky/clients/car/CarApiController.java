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
    private final CarApiService carApiService;

    @GetMapping(path = "/makes")
    ResponseEntity<List<String>> getCarMakes() {
        return ResponseEntity.ok(carApiService.getCarMakes());
    }

    @GetMapping(path = "/types")
    ResponseEntity<List<String>> getCarTypes() {
        return ResponseEntity.ok(carApiService.getCarTypes());
    }

    @GetMapping(path = "/years")
    ResponseEntity<List<Integer>> getCarYears() {
        return ResponseEntity.ok(carApiService.getCarYears());
    }

    @GetMapping
    ResponseEntity<List<String>> getCarModels(
            @RequestParam @Min(1950) Integer year,
            @RequestParam @NotBlank String make,
            @RequestParam @NotBlank String type
    ) {
        return ResponseEntity.ok(carApiService.getCarModels(year, make, type));
    }
}
