package com.viepovsky.clients.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ForecastDto(@JsonProperty("forecast") List<ForecastsDto> forecasts) {}
