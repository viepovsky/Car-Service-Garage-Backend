package com.backend.controller;

import com.backend.domain.AvailableCarService;
import com.backend.domain.dto.AvailableCarServiceDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.AvailableCarServiceMapper;
import com.backend.service.AvailableCarServiceDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/available-car-service")
@RequiredArgsConstructor
public class AvailableCarServiceController {

    private final AvailableCarServiceDbService availableCarServiceDbService;
    private final AvailableCarServiceMapper availableCarServiceMapper;

    @GetMapping
    public ResponseEntity<List<AvailableCarServiceDto>> getAvailableCarServices() {
        List<AvailableCarService> availableCarServiceList= availableCarServiceDbService.getAllAvailableCarService();
        return ResponseEntity.ok(availableCarServiceMapper.mapToAvailableCarServiceDtoList(availableCarServiceList));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createAvailableCarService(@RequestBody AvailableCarServiceDto availableCarServiceDto, @RequestParam(name = "garage-id") Long garageId) throws MyEntityNotFoundException {
        AvailableCarService availableCarService = availableCarServiceMapper.mapToAvailableCarService(availableCarServiceDto);
        availableCarServiceDbService.saveAvailableCarService(availableCarService, garageId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{availableCarServiceId}")
    public ResponseEntity<Void> deleteAvailableCarService(@PathVariable Long availableCarServiceId) throws MyEntityNotFoundException {
        availableCarServiceDbService.deleteAvailableCarService(availableCarServiceId);
        return ResponseEntity.ok().build();
    }
}
