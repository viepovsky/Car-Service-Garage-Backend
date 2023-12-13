package com.viepovsky.clients.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastsDto {
    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("symbolPhrase")
    private String symbolPhrase;

    @JsonProperty("maxTemp")
    private int maxTemp;

    @JsonProperty("minTemp")
    private int minTemp;

    @JsonProperty("maxWindSpeed")
    private int maxWindSpeed;
}
