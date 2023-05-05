package com.viepovsky.carservice;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/car-services")
@RequiredArgsConstructor
@Validated
class CarRepairController {
    private final CarRepairFacade carRepairFacade;

    @GetMapping
    public ResponseEntity<List<CarRepairDto>> getCarServices(@RequestParam @NotBlank String username) throws MyEntityNotFoundException {
        return ResponseEntity.ok(carRepairFacade.getCarServices(username));
    }

    @DeleteMapping(path = "/{carServiceId}")
    public ResponseEntity<Void> deleteCarService(@PathVariable @Min(1) Long carServiceId) throws MyEntityNotFoundException {
        carRepairFacade.deleteCarService(carServiceId);
        return ResponseEntity.ok().build();
    }
}
