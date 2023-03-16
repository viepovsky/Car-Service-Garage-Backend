package com.backend.controller;

import com.backend.config.AdminConfig;
import com.backend.domain.AvailableCarService;
import com.backend.domain.dto.AvailableCarServiceDto;
import com.backend.exceptions.MyEntityNotFoundException;
import com.backend.mapper.AvailableCarServiceMapper;
import com.backend.service.AvailableCarServiceDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/available-car-service")
@RequiredArgsConstructor
@Validated
public class AvailableCarServiceController {

    private final AvailableCarServiceDbService availableCarServiceDbService;
    private final AvailableCarServiceMapper availableCarServiceMapper;
    private final AdminConfig adminConfig;

    @GetMapping(path = "/{garageId}")
    public ResponseEntity<List<AvailableCarServiceDto>> getAvailableCarServices(@PathVariable Long garageId) {
        List<AvailableCarService> availableCarServiceList= availableCarServiceDbService.getAllAvailableCarService(garageId);
        return ResponseEntity.ok(availableCarServiceMapper.mapToAvailableCarServiceDtoList(availableCarServiceList));
    }

    @PostMapping(path = "/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createAvailableCarService(
            @Valid @RequestBody AvailableCarServiceDto availableCarServiceDto,
            @RequestParam(name = "garage-id") @NotNull Long garageId,
            @RequestHeader("api-key") String apiKey
    ) throws MyEntityNotFoundException {
        if(apiKey.equals(adminConfig.getAdminApiKey())){
            AvailableCarService availableCarService = availableCarServiceMapper.mapToAvailableCarService(availableCarServiceDto);
            availableCarServiceDbService.saveAvailableCarService(availableCarService, garageId);
            return ResponseEntity.ok().build();
        }  else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized. Wrong api-key.");
        }
    }

    @DeleteMapping(path = "/admin/{availableCarServiceId}")
    public ResponseEntity<String> deleteAvailableCarService(@PathVariable Long availableCarServiceId, @RequestHeader("api-key") String apiKey) throws MyEntityNotFoundException {
        if(apiKey.equals(adminConfig.getAdminApiKey())){
            availableCarServiceDbService.deleteAvailableCarService(availableCarServiceId);
            return ResponseEntity.ok().build();
        }  else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized. Wrong api-key.");
        }
    }
}
