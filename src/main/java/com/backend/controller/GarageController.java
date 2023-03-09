package com.backend.controller;

import com.backend.config.AdminConfig;
import com.backend.domain.Garage;
import com.backend.domain.dto.GarageDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.GarageMapper;
import com.backend.service.GarageDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/garages")
@RequiredArgsConstructor
@Validated
public class GarageController {
    private final GarageDbService garageDbService;
    private final GarageMapper garageMapper;
    private final AdminConfig adminConfig;

    @GetMapping (path = "/{garageId}")
    public ResponseEntity<GarageDto> getGarage(@PathVariable Long garageId) throws MyEntityNotFoundException {
        Garage garage = garageDbService.getGarage(garageId);
        return ResponseEntity.ok(garageMapper.mapToGarageDto(garage));
    }

    @PostMapping(path = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createGarage(@Valid @RequestBody GarageDto garageDto, @RequestHeader("api-key") String apiKey) {
        if(apiKey.equals(adminConfig.getAdminApiKey())){
            garageDbService.saveGarage(garageMapper.mapToGarage(garageDto));
            return ResponseEntity.ok().build();
        }  else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized. Wrong api-key.");
        }
    }
    @DeleteMapping(path = "/admin/{garageId}")
    public ResponseEntity<String> deleteGarage(@PathVariable Long garageId, @RequestHeader("api-key") String apiKey) throws MyEntityNotFoundException {
        if(apiKey.equals(adminConfig.getAdminApiKey())){
            garageDbService.deleteGarage(garageId);
            return ResponseEntity.ok().build();
        }  else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized. Wrong api-key.");
        }
    }
}
