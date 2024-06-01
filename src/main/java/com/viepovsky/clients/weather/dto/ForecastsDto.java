package com.viepovsky.clients.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ForecastsDto(
        @JsonProperty("day") LocalDate date,
        @JsonProperty("symbol") String symbol,
        @JsonProperty("symbolPhrase") String symbolPhrase,
        @JsonProperty("maxTemp") int maxTemp,
        @JsonProperty("minTemp") int minTemp,
        @JsonProperty("maxWindSpeed") int maxWindSpeed) {}
