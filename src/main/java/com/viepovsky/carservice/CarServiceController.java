package com.viepovsky.carservice;

import com.viepovsky.carservice.CarServiceDto;
import com.viepovsky.exceptions.MyEntityNotFoundException;
import com.viepovsky.carservice.CarServiceFacade;
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
class CarServiceController {
    private final CarServiceFacade carServiceFacade;

    @GetMapping
    public ResponseEntity<List<CarServiceDto>> getCarServices(@RequestParam @NotBlank String username) throws MyEntityNotFoundException {
        return ResponseEntity.ok(carServiceFacade.getCarServices(username));
    }

    @DeleteMapping(path = "/{carServiceId}")
    public ResponseEntity<Void> deleteCarService(@PathVariable @Min(1) Long carServiceId) throws MyEntityNotFoundException {
        carServiceFacade.deleteCarService(carServiceId);
        return ResponseEntity.ok().build();
    }
}
