package com.viepovsky.clients.weather.dto;

import java.time.LocalDate;

public record CityForecastDto(
        LocalDate date,
        String symbol,
        String symbolPhrase,
        int maxTemp,
        int minTemp,
        int maxWindSpeed,
        String city) {}
