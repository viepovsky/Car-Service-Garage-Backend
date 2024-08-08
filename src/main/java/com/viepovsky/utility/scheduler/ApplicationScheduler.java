package com.viepovsky.utility.scheduler;

import com.viepovsky.clients.weather.WeatherApiService;
import com.viepovsky.garage.GarageService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationScheduler.class);
    private final WeatherApiService weatherApiService;
    private final GarageService garageService;

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000, initialDelay = 0)
    public void fetchDataFromWeatherApi() throws InterruptedException {
        List<String> cities = garageService.getAllGarageCities();
        LOGGER.info("Received cities list: {}", cities);
        for (String city : cities) {
            weatherApiService.getAndStore14DaysForecast(city);
        }
    }
}
