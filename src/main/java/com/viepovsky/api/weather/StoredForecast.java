package com.viepovsky.api.weather;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FORECAST")
class StoredForecast {
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
