package com.viepovsky.carrepair;

import com.viepovsky.exceptions.MyEntityNotFoundException;
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
    private final CarRepairFacade facade;

    @GetMapping
    ResponseEntity<List<CarRepairDto>> getCarServices(@RequestParam @NotBlank String username) throws MyEntityNotFoundException {
        String usernameFromToken = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!usernameFromToken.equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(facade.getCarServices(username));
    }

    @DeleteMapping(path = "/{carServiceId}")
    ResponseEntity<Void> deleteCarService(@PathVariable @Min(1) Long carServiceId) throws MyEntityNotFoundException {
        facade.deleteCarService(carServiceId);
        return ResponseEntity.ok().build();
    }
}
