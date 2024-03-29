package com.viepovsky.car_repair;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/car-repairs")
@RequiredArgsConstructor
@Validated
class CarRepairController {
    private final CarRepairFacade carRepairFacade;

    @GetMapping
    ResponseEntity<List<CarRepairDto>> getCarRepairs(@RequestParam @NotBlank String username) {
        String usernameFromToken = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!usernameFromToken.equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(carRepairFacade.getCarRepairs(username));
    }

    @DeleteMapping(path = "/{carRepairId}")
    ResponseEntity<Void> deleteCarRepair(@PathVariable @Min(1) Long carRepairId) {
        carRepairFacade.deleteCarRepair(carRepairId);
        return ResponseEntity.ok().build();
    }
}
