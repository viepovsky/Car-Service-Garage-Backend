package com.viepovsky.garage.availableservice;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/available-car-service")
@RequiredArgsConstructor
@Validated
class AvailableCarServiceController {
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
