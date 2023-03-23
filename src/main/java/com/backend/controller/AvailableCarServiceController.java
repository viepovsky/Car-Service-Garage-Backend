package com.backend.controller;

import com.backend.domain.dto.AvailableCarServiceDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.facade.AvailableCarServiceFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/available-car-service")
@RequiredArgsConstructor
@Validated
public class AvailableCarServiceController {
    private final AvailableCarServiceFacade availableCarServiceFacade;

    @GetMapping(path = "/{garageId}")
    public ResponseEntity<List<AvailableCarServiceDto>> getAvailableCarServices(@PathVariable @Min(1) Long garageId) {
        return ResponseEntity.ok(availableCarServiceFacade.getAvailableCarServices(garageId));
    }

    @PostMapping(path = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAvailableCarService(
            @Valid @RequestBody AvailableCarServiceDto availableCarServiceDto,
            @RequestParam(name = "garage-id") @NotNull @Min(1) Long garageId,
            @RequestHeader("api-key") @NotBlank String apiKey
    ) throws MyEntityNotFoundException {
        return availableCarServiceFacade.createAvailableCarService(availableCarServiceDto, garageId, apiKey);
    }

    @DeleteMapping(path = "/admin/{availableCarServiceId}")
    public ResponseEntity<String> deleteAvailableCarService(
            @PathVariable @Min(1) Long availableCarServiceId,
            @RequestHeader("api-key") @NotBlank String apiKey
    ) throws MyEntityNotFoundException {
        return availableCarServiceFacade.deleteAvailableCarService(availableCarServiceId, apiKey);
    }
}
