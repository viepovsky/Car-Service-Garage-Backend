package com.backend.controller;

import com.backend.domain.Garage;
import com.backend.domain.dto.GarageDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.GarageMapper;
import com.backend.service.GarageDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/garages")
@RequiredArgsConstructor
public class GarageController {
    private final GarageDbService garageDbService;
    private final GarageMapper garageMapper;

    @GetMapping (path = "/{garageId}")
    public ResponseEntity<GarageDto> getGarage(@PathVariable Long garageId) throws MyEntityNotFoundException {
        Garage garage = garageDbService.getGarage(garageId);
        return ResponseEntity.ok(garageMapper.mapToGarageDto(garage));
    }
}
