package com.backend.api.weather.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CARS_API")
public class StoredForecast {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "symbol_phrase")
    private String symbolPhrase;

    @Column(name = "max_temp")
    private int maxTemp;

    @Column(name = "min_temp")
    private int minTemp;

    @Column(name = "max_wind_speed")
    private int maxWindSpeed;

    @Column(name = "city")
    private String city;

    public StoredForecast(LocalDate date, String symbol, String symbolPhrase, int maxTemp, int minTemp, int maxWindSpeed, String city) {
        this.date = date;
        this.symbol = symbol;
        this.symbolPhrase = symbolPhrase;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.maxWindSpeed = maxWindSpeed;
        this.city = city;
    }
}
