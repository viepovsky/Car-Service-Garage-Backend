package com.backend.controller;

import com.backend.domain.GarageWorkTime;
import com.backend.domain.dto.GarageWorkTimeDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.GarageWorkTimeMapper;
import com.backend.service.GarageWorkTimeDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/garage-work-time")
@RequiredArgsConstructor
public class GarageWorkTimeController {
    private final GarageWorkTimeDbService garageWorkTimeDbService;
    private final GarageWorkTimeMapper garageWorkTimeMapper;

    @GetMapping
    public ResponseEntity<List<GarageWorkTimeDto>> getGarageWorkTimes() {
        List<GarageWorkTime> garageWorkTimeList = garageWorkTimeDbService.getAllGarageWorkTimes();
        return ResponseEntity.ok(garageWorkTimeMapper.mapToGarageWorkTimeDtoList(garageWorkTimeList));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/{garageId}")
    public ResponseEntity<Void> createGarageWorkTime(@RequestBody GarageWorkTimeDto garageWorkTimeDto, @PathVariable Long garageId) throws MyEntityNotFoundException {
        GarageWorkTime garageWorkTime = garageWorkTimeMapper.mapToGarageWorkTime(garageWorkTimeDto);
        garageWorkTimeDbService.saveGarageWorkTime(garageWorkTime, garageId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{garageWorkTimeId}")
    public ResponseEntity<Void> deleteGarageWorkTime(@PathVariable Long garageWorkTimeId) throws MyEntityNotFoundException {
        garageWorkTimeDbService.deleteGarageWorkTime(garageWorkTimeId);
        return ResponseEntity.ok().build();
    }
}
