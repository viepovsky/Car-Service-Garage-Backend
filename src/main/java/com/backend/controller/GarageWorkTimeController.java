package com.backend.controller;

import com.backend.config.AdminConfig;
import com.backend.domain.GarageWorkTime;
import com.backend.domain.dto.GarageWorkTimeDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.GarageWorkTimeMapper;
import com.backend.service.GarageWorkTimeDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/garage-work-time")
@RequiredArgsConstructor
@Validated
public class GarageWorkTimeController {
    private final GarageWorkTimeDbService garageWorkTimeDbService;
    private final GarageWorkTimeMapper garageWorkTimeMapper;
    private final AdminConfig adminConfig;

    @GetMapping
    public ResponseEntity<List<GarageWorkTimeDto>> getGarageWorkTimes() {
        List<GarageWorkTime> garageWorkTimeList = garageWorkTimeDbService.getAllGarageWorkTimes();
        return ResponseEntity.ok(garageWorkTimeMapper.mapToGarageWorkTimeDtoList(garageWorkTimeList));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/admin/{garageId}")
    public ResponseEntity<String> createGarageWorkTime(
            @Valid @RequestBody GarageWorkTimeDto garageWorkTimeDto,
            @PathVariable Long garageId,
            @RequestHeader("api-key") String apiKey
    ) throws MyEntityNotFoundException {
        if(apiKey.equals(adminConfig.getAdminApiKey())){
            GarageWorkTime garageWorkTime = garageWorkTimeMapper.mapToGarageWorkTime(garageWorkTimeDto);
            garageWorkTimeDbService.saveGarageWorkTime(garageWorkTime, garageId);
            return ResponseEntity.ok().build();
        }  else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized. Wrong api-key.");
        }
    }

    @DeleteMapping(path = "/admin/{garageWorkTimeId}")
    public ResponseEntity<String> deleteGarageWorkTime(@PathVariable Long garageWorkTimeId, @RequestHeader("api-key") String apiKey) throws MyEntityNotFoundException {
        if(apiKey.equals(adminConfig.getAdminApiKey())){
            garageWorkTimeDbService.deleteGarageWorkTime(garageWorkTimeId);
            return ResponseEntity.ok().build();
        }  else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized. Wrong api-key.");
        }
    }
}
