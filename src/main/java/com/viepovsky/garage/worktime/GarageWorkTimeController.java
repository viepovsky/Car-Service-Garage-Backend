package com.viepovsky.garage.worktime;

import com.viepovsky.exceptions.MyEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/garage-work-time")
@RequiredArgsConstructor
@Validated
class GarageWorkTimeController {
    private final GarageWorkTimeFacade garageWorkTimeFacade;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/admin/{garageId}")
    public ResponseEntity<String> createGarageWorkTime(
            @Valid @RequestBody GarageWorkTimeDto garageWorkTimeDto,
            @PathVariable @Min(1) Long garageId,
            @RequestHeader("api-key") @NotBlank String apiKey
    ) throws MyEntityNotFoundException {
        return garageWorkTimeFacade.createGarageWorkTime(garageWorkTimeDto, garageId, apiKey);
    }

    @DeleteMapping(path = "/admin/{garageWorkTimeId}")
    public ResponseEntity<String> deleteGarageWorkTime(
            @PathVariable @Min(1) Long garageWorkTimeId,
            @RequestHeader("api-key") @NotBlank String apiKey
    ) throws MyEntityNotFoundException {
        return garageWorkTimeFacade.deleteGarageWorkTime(garageWorkTimeId, apiKey);
    }
}
