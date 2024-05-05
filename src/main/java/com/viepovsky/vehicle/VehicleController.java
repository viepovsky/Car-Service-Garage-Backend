package com.viepovsky.vehicle;

import com.viepovsky.vehicle.dto.VehicleDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/vehicles")
@RequiredArgsConstructor
@Validated
class VehicleController {
    private final VehicleFacade vehicleFacade;

    @GetMapping(path = "/{vehicleId}")
    ResponseEntity<VehicleDto> getVehicle(@PathVariable @Min(1) Long vehicleId) {
        return ResponseEntity.ok(vehicleFacade.getVehicle(vehicleId));
    }

    @GetMapping
    ResponseEntity<List<VehicleDto>> getVehiclesByUsername(
            @RequestParam(name = "username") @NotBlank String username) {
        return ResponseEntity.ok(vehicleFacade.getVehiclesByUsername(username));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createVehicle(
            @Valid @RequestBody VehicleDto vehicleDto,
            @RequestParam(name = "username") @NotBlank String username) {
        vehicleFacade.createVehicle(vehicleDto, username);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updateVehicle(@Valid @RequestBody VehicleDto vehicleDto) {
        vehicleFacade.updateVehicle(vehicleDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{vehicleId}")
    ResponseEntity<Void> deleteVehicle(@PathVariable @Min(1) Long vehicleId) {
        vehicleFacade.deleteVehicle(vehicleId);
        return ResponseEntity.ok().build();
    }
}
