package com.backend.controller;

import com.backend.domain.dto.GarageDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.facade.GarageFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/garages")
@RequiredArgsConstructor
@Validated
public class GarageController {
    private final GarageFacade garageFacade;

    @GetMapping
    public ResponseEntity<List<GarageDto>> getGarages() {
        return ResponseEntity.ok(garageFacade.getAllGarages());
    }

    @PostMapping(path = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createGarage(
            @Valid @RequestBody GarageDto garageDto,
            @RequestHeader("api-key") @NotBlank String apiKey
    ) {
        return garageFacade.createGarage(garageDto, apiKey);
    }
    @DeleteMapping(path = "/admin/{garageId}")
    public ResponseEntity<String> deleteGarage(
            @PathVariable @Min(1) Long garageId,
            @RequestHeader("api-key") @NotBlank String apiKey
    ) throws MyEntityNotFoundException {
        return garageFacade.deleteGarage(garageId, apiKey);
    }
}
