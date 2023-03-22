package com.backend.api.weather.controller;

import com.backend.api.weather.domain.CityForecastDto;
import com.backend.api.weather.service.WeatherApiService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/weather-api")
@RequiredArgsConstructor
public class WeatherApiController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherApiController.class);
    private final WeatherApiService weatherApiService;

    @GetMapping
    public ResponseEntity<CityForecastDto> getForecastForCityAndDate(
            @RequestParam(name = "city") @NotEmpty String city,
            @RequestParam(name = "date") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ){
        LOGGER.info("GET Endpoint getForecastForCityAndDate used.");
        CityForecastDto cityForecastDto = weatherApiService.getForecastForCityAndDate(city, date);
        return ResponseEntity.ok(cityForecastDto);
    }
}
