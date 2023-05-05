package com.viepovsky.garage.availablerepair;

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
class AvailableCarRepairController {
    private final AvailableCarRepairFacade availableCarRepairFacade;

    @GetMapping(path = "/{garageId}")
    public ResponseEntity<List<AvailableCarRepairDto>> getAvailableCarServices(@PathVariable @Min(1) Long garageId) {
        return ResponseEntity.ok(availableCarRepairFacade.getAvailableCarServices(garageId));
    }

    @PostMapping(path = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAvailableCarService(
            @Valid @RequestBody AvailableCarRepairDto availableCarRepairDto,
            @RequestParam(name = "garage-id") @NotNull @Min(1) Long garageId,
            @RequestHeader("api-key") @NotBlank String apiKey
    ) throws MyEntityNotFoundException {
        return availableCarRepairFacade.createAvailableCarService(availableCarRepairDto, garageId, apiKey);
    }

    @DeleteMapping(path = "/admin/{availableCarServiceId}")
    public ResponseEntity<String> deleteAvailableCarService(
            @PathVariable @Min(1) Long availableCarServiceId,
            @RequestHeader("api-key") @NotBlank String apiKey
    ) throws MyEntityNotFoundException {
        return availableCarRepairFacade.deleteAvailableCarService(availableCarServiceId, apiKey);
    }
}
