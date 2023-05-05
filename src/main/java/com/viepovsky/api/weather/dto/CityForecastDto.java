package com.viepovsky.api.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityForecastDto {
    private LocalDate date;

    private String symbol;

    private String symbolPhrase;

    private int maxTemp;

    private int minTemp;

    private int maxWindSpeed;

    private String city;
}
