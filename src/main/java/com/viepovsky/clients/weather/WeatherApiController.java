package com.viepovsky.clients.weather;

import com.viepovsky.clients.weather.dto.CityForecastDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/weather-api")
@RequiredArgsConstructor
class WeatherApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherApiController.class);
    private final WeatherApiService service;

    @GetMapping
    ResponseEntity<CityForecastDto> getForecastForCityAndDate(
            @RequestParam(name = "city") @NotBlank String city,
            @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        LOGGER.info("GET Endpoint getForecastForCityAndDate used.");
        CityForecastDto cityForecastDto = service.getForecastForCityAndDate(city, date);
        return ResponseEntity.ok(cityForecastDto);
    }
}
