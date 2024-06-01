package com.viepovsky.clients.car;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/car-api")
@RequiredArgsConstructor
@Validated
class CarApiController {

    private final CarApiService carApiService;

    @GetMapping
    ResponseEntity<List<String>> getCarModels(
            @RequestParam @Min(1900) Integer year,
            @RequestParam @NotBlank String make,
            @RequestParam @NotBlank String type) {
        return ResponseEntity.ok(carApiService.getCarModels(year, make, type));
    }
}
